package com.example.authservice.controller;


import com.example.authservice.dto.LoginRequestDTO;
import com.example.authservice.dto.LoginResponseDTO;
import com.example.authservice.dto.RegisterDTO;
import com.example.authservice.model.User;
import com.example.authservice.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @Operation(summary = "generate token on login")
    @PostMapping(path = "/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {

        Optional<String>tokenOptional = authService.authenticate(loginRequestDTO);

        if(tokenOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = tokenOptional.get();
        return ResponseEntity.ok( new LoginResponseDTO(token));

    }

    @Operation(summary = "create auth user")
    @PostMapping(path="/signup")
    public ResponseEntity<Void> signup(@RequestBody RegisterDTO registerDTO) {
        boolean isUserPresent = authService.register(registerDTO);
        if(isUserPresent){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "validate token")
    @GetMapping(path = "/validate")
    public ResponseEntity<Void> validateToken(@RequestHeader("Authorization") String token) {
        if( token == null || !token.startsWith("Bearer ") ){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return authService.validateToken(token.substring(7)) ? ResponseEntity.ok().build() :
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
