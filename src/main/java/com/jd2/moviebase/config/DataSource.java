package com.jd2.moviebase.config;

import com.jd2.moviebase.util.PropertyHelper;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {
    private final HikariDataSource ds;
    PropertyHelper propertyHelper = new PropertyHelper();

    public DataSource() {
        this.ds = new HikariDataSource();
        this.ds.setJdbcUrl(propertyHelper.getProperty("db.host"));
        this.ds.setUsername(propertyHelper.getProperty("db.username"));
        this.ds.setPassword(propertyHelper.getProperty("db.password"));
    }

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
