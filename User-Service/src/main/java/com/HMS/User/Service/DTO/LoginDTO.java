package com.HMS.User.Service.DTO;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginDTO {

    private String email;

    private String password;
}
