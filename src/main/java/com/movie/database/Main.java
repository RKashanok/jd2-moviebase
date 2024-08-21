package com.movie.database;

import com.movie.database.datasource.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        try {
            DataSource ds = new DataSource();
            Connection connection = ds.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("email");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                String createdAt = resultSet.getTimestamp("created_at").toString();

                System.out.println("ID: " + id + ", Username: " + username + ", Password: " + password +
                        ", Email: " + email + ", Created At: " + createdAt);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
