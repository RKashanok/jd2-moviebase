package com.jd2.moviebase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class MovieBaseApplication {
    public static void main(String[] args) {
        SpringApplication.run(MovieBaseApplication.class, args);
    }
}
