package org.example.catmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"org.example.catmicroservice"})
@EntityScan(basePackages = "org.example.models")
@EnableJpaRepositories("org.example.catmicroservice.repository")
public class CatMicroServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CatMicroServiceApplication.class, args);
    }
}
