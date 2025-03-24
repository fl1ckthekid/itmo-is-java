package org.example.externalmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = {"org.example.externalmicroservice", "org.example.models"})
@EntityScan(basePackages = "org.example.models")
public class ExternalMicroServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExternalMicroServiceApplication.class, args);
    }
}
