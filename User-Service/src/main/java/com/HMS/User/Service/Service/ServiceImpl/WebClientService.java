package com.HMS.User.Service.Service.ServiceImpl;

import com.HMS.User.Service.DTO.UserDTO;
import com.HMS.User.Service.Entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class WebClientService {

    @Autowired
    private WebClient.Builder webClient;

    public Mono<Long> addProfile(UserDTO userDTO){
        System.out.println("Role: " + userDTO.getRole());
        if(Role.DOCTOR.equals(userDTO.getRole())){
            System.out.println("doctor");
            return webClient.build().post().uri("http://localhost:1001/profile/doctor/add")
                    .bodyValue(userDTO).retrieve().bodyToMono(Long.class);
        }else if(Role.PATIENT.equals(userDTO.getRole())){
            return webClient.build().post().uri("http://localhost:1001/profile/patient/add")
                    .bodyValue(userDTO).retrieve().bodyToMono(Long.class);
        }
        return null;
    }

}
