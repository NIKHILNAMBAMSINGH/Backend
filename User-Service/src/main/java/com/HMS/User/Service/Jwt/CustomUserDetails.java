package com.HMS.User.Service.Jwt;

import com.HMS.User.Service.Entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomUserDetails  implements UserDetails {
    private Long id;
    private String username;
    private String email;
    private String password;
    private Role role;
    private String name;
    private Collection<? extends GrantedAuthority>authorities;
}
