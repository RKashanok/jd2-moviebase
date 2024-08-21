package com.movie.database.datasource;

import com.helpers.PropertyHelper;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static com.helpers.ConstantsHelper.APP;

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
