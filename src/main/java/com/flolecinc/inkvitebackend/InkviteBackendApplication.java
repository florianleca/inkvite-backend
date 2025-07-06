package com.flolecinc.inkvitebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class InkviteBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(InkviteBackendApplication.class, args);
    }

}
