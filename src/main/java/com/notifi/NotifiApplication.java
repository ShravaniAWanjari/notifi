package com.notifi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class NotifiApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotifiApplication.class, args);
    }

}
