package com.example.authservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterDTO {

    @NotBlank(message = "Email is required to register")
    @Email
    private String email;

    @NotBlank(message = "Name is required to register")
    @Size(max=50 , message = "Name exceeded 50 characters")
    private String name;

    @NotBlank(message = "Password is required to register")
    @Size(min=8 , message = "Password should have atleast 8 characters")
    private String password;

    @NotBlank(message = "Role is required for the user")
    private String role;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
}
