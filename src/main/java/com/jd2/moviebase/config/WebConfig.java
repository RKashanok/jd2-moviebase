package com.jd2.moviebase.config;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableJpaRepositories
public class WebConfig {
    @Bean
    public OkHttpClient okHttpClient() {
        System.out.println("OkHttpClient started");
        return new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build();
    }


}