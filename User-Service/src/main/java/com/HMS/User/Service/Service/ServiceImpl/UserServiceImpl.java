package com.HMS.User.Service.Service.ServiceImpl;

import com.HMS.User.Service.DTO.LoginDTO;
import com.HMS.User.Service.DTO.UserDTO;
import com.HMS.User.Service.Entity.User;
import com.HMS.User.Service.Exception.HsException;
import com.HMS.User.Service.Repository.UserRepository;
import com.HMS.User.Service.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebClientService webClientService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    @Transactional
    public void registerUser(UserDTO userDTO) throws HsException {
        Optional<User> userOptional=userRepository.findByEmail(userDTO.getEmail());
        if (userOptional.isPresent()){
            throw new HsException("User already registered ");
        }
        System.out.println(userDTO);
        Long profileId;
        try {
            profileId = webClientService.addProfile(userDTO).block();
        } catch (WebClientResponseException e) {
            System.out.println("Error response body: " + e.getResponseBodyAsString());
            throw new HsException("Failed to create profile:");
        }
        User user=new User();
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        user.setRole(userDTO.getRole());
        user.setPassword(passwordEncoder.encode(userDTO.getPasword()));
        user.setProfileId(profileId);
        userRepository.save(user);
    }

    @Override
    public UserDTO loginUser(LoginDTO userDTO) throws HsException {
        User user=userRepository.findByEmail(userDTO.getEmail()).
                orElseThrow(()->new HsException("User not found"));
        if(!passwordEncoder.matches(userDTO.getPassword(), user.getPassword())){
            throw new HsException("Invalid Credentials");
        }
        UserDTO dbUser=new UserDTO();
        dbUser.setEmail(user.getEmail());
        dbUser.setRole(user.getRole());
        dbUser.setPassword(null);
        dbUser.setName(user.getName());
        return dbUser;
    }

    @Override
    public User getUserById(Long id) throws HsException {
        User user=userRepository.findById(id).orElseThrow(()->new HsException("User not found"));

        User dbUser=new User();
        dbUser.setEmail(user.getEmail());
        dbUser.setRole(user.getRole());
        dbUser.setPassword(null);
        dbUser.setName(user.getName());
        return dbUser;
    }

    @Override
    public void updateUser(UserDTO userDTO) {

    }

    @Override
    public User getUser(String email) throws HsException {
        Optional<User>userOptional=userRepository.findByEmail(email);
        if(userOptional.isEmpty()){
            throw new HsException("User not found");
        }
        User dbUser=new User();
        //dbUser.setId(userOptional.get().getId());
        dbUser.setEmail(userOptional.get().getEmail());
        dbUser.setRole(userOptional.get().getRole());
        dbUser.setPassword(userOptional.get().getPassword());
        dbUser.setName(userOptional.get().getName());
        dbUser.setProfileId(userOptional.get().getProfileId());
        return dbUser;
    }
}
