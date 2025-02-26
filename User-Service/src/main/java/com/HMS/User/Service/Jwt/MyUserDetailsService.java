package com.HMS.User.Service.Jwt;

import com.HMS.User.Service.DTO.UserDTO;
import com.HMS.User.Service.Entity.User;
import com.HMS.User.Service.Exception.HsException;
import com.HMS.User.Service.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            System.out.println("🚀 Fetching user from database: " + email);
            User user=userService.getUser(email);
            CustomUserDetails customUserDetails=new CustomUserDetails();
            customUserDetails.setId(user.getId());
            customUserDetails.setUsername(user.getName());
            customUserDetails.setPassword(user.getPassword());
            customUserDetails.setEmail(user.getEmail());
            customUserDetails.setRole(user.getRole());
            customUserDetails.setAuthorities(null);
            return customUserDetails;
        } catch (HsException e) {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
