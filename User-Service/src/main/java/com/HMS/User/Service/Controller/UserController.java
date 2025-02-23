package com.HMS.User.Service.Controller;


import com.HMS.User.Service.DTO.LoginDTO;
import com.HMS.User.Service.DTO.UserDTO;
import com.HMS.User.Service.Entity.User;
import com.HMS.User.Service.Exception.HsException;
import com.HMS.User.Service.Jwt.JwtUtils;
import com.HMS.User.Service.Repository.UserRepository;
import com.HMS.User.Service.Service.UserService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.spring.boot.client.MatrixToImageWriter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils  jwtUtils;
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager  authenticationManager;
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?>registerUser(@Valid @RequestBody UserDTO userDTO) throws HsException {
        userService.registerUser(userDTO);
        Map<String,Object>response=new HashMap<>();
        response.put("status", 1);
        response.put("message","Created User");

        return new ResponseEntity<>(response,HttpStatus.CREATED);

    }
    @PostMapping("/login")
    public ResponseEntity<?>loginUser(@Valid @RequestBody LoginDTO loginDTO) throws HsException {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());
            authenticationManager.authenticate(authenticationToken);
        }
        catch (AuthenticationException e){
            throw new HsException("Invalid Credentials");
        }
        System.out.println(loginDTO.getEmail()+""+loginDTO.getPassword());
        final UserDetails userDetails=userDetailsService.loadUserByUsername(loginDTO.getEmail());
        System.out.println(userDetails);
        final String jwt= jwtUtils.generateToken(userDetails);
        return new ResponseEntity<>(jwt,HttpStatus.OK);

    }

    @GetMapping("/getData")
    public ResponseEntity<byte[]> getDataUser() throws WriterException, IOException {
        List<User> list = userRepository.findAll();

        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        User firstUser = list.get(0);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        BitMatrix bitMatrix = qrCodeWriter.encode(
                "Name: " + firstUser.getName() + "\n" +
                        "Email: " + firstUser.getEmail() + "\n",
                BarcodeFormat.QR_CODE, 100, 50);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", baos);
        byte[] qrCodeBytes = baos.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(qrCodeBytes.length);

        return new ResponseEntity<>(qrCodeBytes, headers, HttpStatus.OK);
    }


}
