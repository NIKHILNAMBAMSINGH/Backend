package com.HMS.User.Service.Service;

import com.HMS.User.Service.DTO.LoginDTO;
import com.HMS.User.Service.DTO.UserDTO;
import com.HMS.User.Service.Entity.User;
import com.HMS.User.Service.Exception.HsException;

public interface UserService {
    public void registerUser(UserDTO userDTO) throws HsException;
    public UserDTO loginUser(LoginDTO loginDTO) throws HsException;
    public User getUserById(Long id) throws HsException;
    public void updateUser(UserDTO userDTO);

    public User getUser(String email) throws HsException;
}
