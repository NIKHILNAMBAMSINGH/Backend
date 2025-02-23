package com.hms.GatewayHms.Filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.ObjectInputFilter;
import java.nio.charset.StandardCharsets;

@Component
public class TokenFilter extends AbstractGatewayFilterFactory<TokenFilter.Config> {

    private static final String SECRET_KEY = "4b2247697c4ac6ab31b6a477f65e486d18bf46830ea5f733650c35702f6a0cbf08b9bd12b12061ce9c04de67934abe9b3e05e7a28db2072b6fadd381a507874e";

    public TokenFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            System.out.println("Request Path: " + exchange.getRequest().getPath());

            String path = exchange.getRequest().getPath().toString();

            // Allow login and register endpoints without authentication
            if (path.equals("/user/login") || path.equals("/user/register")) {
                return chain.filter(exchange.mutate().request(r->r.header("X-Secret-Key","SECRET")).build());
            }

            HttpHeaders headers = exchange.getRequest().getHeaders();

            // Validate Authorization header
            if (!headers.containsKey(HttpHeaders.AUTHORIZATION)) {
                throw new RuntimeException("Authorization header is missing");
            }

            String authHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
              throw new RuntimeException("Invalid Authorization header format");
            }

            String token = authHeader.substring(7);

            try {
                // Validate JWT token
                Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
                exchange=exchange.mutate().request(r->r.header("X-Secret-Key","SECRET")).build();
                // Token is valid, continue request
                return chain.filter(exchange);
            } catch (Exception e) {
                throw  new RuntimeException("Invalid or expired token");
            }
        };
    }

    // Method to send a 401 Unauthorized response

    public static class Config {
        // Placeholder for future configurations
    }
}