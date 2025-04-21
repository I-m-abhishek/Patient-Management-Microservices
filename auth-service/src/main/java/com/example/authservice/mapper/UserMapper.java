package com.example.authservice.mapper;

import com.example.authservice.dto.RegisterDTO;
import com.example.authservice.model.User;

import java.util.Date;

public class UserMapper {

    public static User RegisterMapper(RegisterDTO registerDTO){
            User newUser = new User();
            newUser.setEmail(registerDTO.getEmail());
            newUser.setPassword(registerDTO.getPassword());
            newUser.setName(registerDTO.getName());
            newUser.setRole(registerDTO.getRole());
            newUser.setRegistration_date(String.valueOf(new Date()));
            return newUser;
    }

}
