package com.example.unifateciedev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin(origins = "https://18.231.27.198:3000/login")
public class UnifatecieDevApplication {

    public static void main(String[] args) {
        SpringApplication.run(UnifatecieDevApplication.class, args);
    }

}
