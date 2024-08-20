package com.repository;

import com.movie.database.datasource.DataSource;
import com.moviebase.beans.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {
    private final DataSource ds;

    public UserRepository(DataSource ds) {
        this.ds = ds;
    }

    public User create(User user) {
        try(Connection conn = ds.getConnection())  {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO users (email, password, role, created_at, updated_at) VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole());
            ps.setTimestamp(4, new java.sql.Timestamp(user.getCreatedAt().getTime()));
            ps.setTimestamp(5, new java.sql.Timestamp(user.getUpdatedAt().getTime()));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public User findById(int id) {
        User user = new User();
        try(Connection conn = ds.getConnection())  {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE id = ?");
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(resultSet.getString("role"));
                user.setCreatedAt(resultSet.getDate("created_at"));
                user.setUpdatedAt(resultSet.getDate("updated_at"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public User update(User user) {
        try(Connection conn = ds.getConnection())  {
            PreparedStatement ps = conn.prepareStatement("UPDATE users SET email = ?, password = ?, role = ?, updated_at = ? WHERE id = ?");
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole());
            ps.setTimestamp(4, new java.sql.Timestamp(user.getUpdatedAt().getTime()));
            ps.setInt(5, user.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public void deleteById(int id) {
        try(Connection conn = ds.getConnection())  {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM users WHERE id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
