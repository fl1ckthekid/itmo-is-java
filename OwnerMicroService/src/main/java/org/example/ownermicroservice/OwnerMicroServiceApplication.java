package org.example.ownermicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"org.example.ownermicroservice"})
@EntityScan(basePackages = "org.example.models")
@EnableJpaRepositories("org.example.ownermicroservice.repository")
public class OwnerMicroServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OwnerMicroServiceApplication.class, args);
    }
}
