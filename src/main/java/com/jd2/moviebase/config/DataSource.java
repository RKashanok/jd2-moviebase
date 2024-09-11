package com.jd2.moviebase.config;

import com.jd2.moviebase.util.PropertyHelper;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

@Component
public class DataSource {
    private final HikariDataSource ds;
    PropertyHelper propertyHelper = new PropertyHelper();

    public DataSource() throws ClassNotFoundException {
        System.out.println("Start DataSource");
        Class.forName("org.postgresql.Driver");
        this.ds = new HikariDataSource();
        this.ds.setJdbcUrl(propertyHelper.getProperty("db.host"));
        this.ds.setUsername(propertyHelper.getProperty("db.username"));
        this.ds.setPassword(propertyHelper.getProperty("db.password"));
    }

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
