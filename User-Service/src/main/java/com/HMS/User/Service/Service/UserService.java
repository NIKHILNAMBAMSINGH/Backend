package com.HMS.User.Service.Service;

import com.HMS.User.Service.DTO.LoginDTO;
import com.HMS.User.Service.DTO.UserDTO;
import com.HMS.User.Service.Exception.HsException;

public interface UserService {
    public void registerUser(UserDTO userDTO) throws HsException;
    public UserDTO loginUser(LoginDTO loginDTO) throws HsException;
    public UserDTO getUserById(Long id) throws HsException;
    public void updateUser(UserDTO userDTO);

    public UserDTO getUser(String email) throws HsException;
}
