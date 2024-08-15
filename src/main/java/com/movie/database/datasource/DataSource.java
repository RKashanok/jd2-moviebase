package com.movie.database.datasource;

import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static com.helpers.ConstantsHelper.APP;
import static com.helpers.PropertyHelper.getProperty;

public class DataSource {
    private final HikariDataSource ds;

    public DataSource() {
        this.ds = new HikariDataSource();
        this.ds.setJdbcUrl(getProperty(APP, "db.host"));
        this.ds.setUsername(getProperty(APP, "db.username"));
        this.ds.setPassword(getProperty(APP, "db.password"));
    }

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
