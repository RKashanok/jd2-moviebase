package com.jd2.moviebase.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;

import javax.sql.DataSource;
import java.util.concurrent.TimeUnit;

@Configuration
@EntityScan("com.jd2.moviebase.model")
@EnableJpaRepositories("com.jd2.moviebase.repository")
public class WebConfig {

    @Bean
    public DataSource dataSource() {
        System.out.println("DataSource started");
        return DataSourceBuilder.create().build();
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        System.out.println("JpaTransactionManager started");
        return new JpaTransactionManager();
    }

    @Bean
    public OkHttpClient okHttpClient() {
        System.out.println("OkHttpClient started");
        return new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build();
    }

    @Bean
    ObjectMapper objectMapper() {
        return new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
}