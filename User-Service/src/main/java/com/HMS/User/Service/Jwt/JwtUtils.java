package com.HMS.User.Service.Jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class JwtUtils {

    public static final Long JWT_VALIDITY=5*60*60L;
    public static final String SECRET_KEY="4b2247697c4ac6ab31b6a477f65e486d18bf46830ea5f733650c35702f6a0cbf08b9bd12b12061ce9c04de67934abe9b3e05e7a28db2072b6fadd381a507874e";

    public String generateToken(UserDetails userDetails){
        Map<String,Object>claims=new HashMap<>();
        CustomUserDetails customUserDetails=(CustomUserDetails) userDetails;
        claims.put("id",customUserDetails.getId());
        claims.put("email",customUserDetails.getEmail());
        claims.put("role",customUserDetails.getRole());
        claims.put("name",customUserDetails.getUsername());
        System.out.println("Claims: " + claims);
        return doGenerateToken(claims, customUserDetails.getEmail());

    }
    public String doGenerateToken(Map<String, Object>claims,String subject){
        return Jwts.builder().setSubject(subject).addClaims(claims).setIssuedAt(new Date(System.currentTimeMillis())).
                setExpiration(new Date(System.currentTimeMillis()+JWT_VALIDITY*1000)).
                signWith(SignatureAlgorithm.HS512,SECRET_KEY).compact();
    }

}
