package com.team5.sparta_clone_5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpartaClone5Application {

    public static void main(String[] args) {
        SpringApplication.run(SpartaClone5Application.class, args);
    }

}
