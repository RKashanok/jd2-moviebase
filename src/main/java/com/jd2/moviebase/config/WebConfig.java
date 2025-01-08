package com.jd2.moviebase.config;

import okhttp3.OkHttpClient;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.concurrent.TimeUnit;

@Configuration
@EntityScan("com.jd2.moviebase.model")
@EnableJpaRepositories("com.jd2.moviebase.repository")
public class WebConfig {
    @Bean
    public OkHttpClient okHttpClient() {
        System.out.println("OkHttpClient started");
        return new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build();
    }


}