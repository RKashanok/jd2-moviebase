package com.jd2.moviebase.config;

import com.jd2.moviebase.util.PropertyHelper;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:app.properties")
public class SpringConfig {

    @Value("${db.username}")
    private String dbUsername;
    @Value("${db.password}")
    private String dbPassword;
    @Value("${db.host}")
    private String dbHost;
    @Value("${db.driver}")
    private String dbDriver;

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();

        config.setUsername(dbUsername);
        config.setPassword(dbPassword);
        config.setJdbcUrl(dbHost);
        config.setDriverClassName(dbDriver);

        return new HikariDataSource(config);
    }


}
