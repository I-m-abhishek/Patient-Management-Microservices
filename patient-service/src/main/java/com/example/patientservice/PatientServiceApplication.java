package com.example.patientservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class PatientServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(PatientServiceApplication.class, args);

    }

    @RestController
    public class student{

        @GetMapping
        public String student(){
            return "student";
        }

    }
}
