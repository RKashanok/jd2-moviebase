package com.jd2.moviebase.config;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {

    private final HikariDataSource ds;

    public DataSource() {
        this.ds = new HikariDataSource();
        this.ds.setJdbcUrl("jdbc:postgres:localhost:5432/testdb");
        this.ds.setUsername("sa");
        this.ds.setPassword("");
    }

    public Connection getConnection() throws SQLException {
            return ds.getConnection();
        }

}
