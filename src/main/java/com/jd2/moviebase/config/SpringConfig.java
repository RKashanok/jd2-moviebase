package com.jd2.moviebase.config;

import com.jd2.moviebase.util.PropertyHelper;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        PropertyHelper propertyHelper = new PropertyHelper();

        config.setJdbcUrl(propertyHelper.getProperty("db.host"));
        config.setUsername(propertyHelper.getProperty("db.username"));
        config.setPassword(propertyHelper.getProperty("db.password"));
        config.setDriverClassName(propertyHelper.getProperty("db.driver"));

        return new HikariDataSource(config);
    }


}
