package com.jd2.moviebase.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MovieBaseApplication {
    public static void main(String[] args) {
        System.out.println("MovieBaseApplication started");
        SpringApplication.run(MovieBaseApplication.class, args);
    }
}
