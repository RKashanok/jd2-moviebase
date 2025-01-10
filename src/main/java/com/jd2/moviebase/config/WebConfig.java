package com.jd2.moviebase.config;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class WebConfig {
    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build();
    }


}