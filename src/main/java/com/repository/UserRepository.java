package com.repository;

import com.movie.database.datasource.DataSource;
import com.moviebase.beans.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {
    private final DataSource ds;
    private final String CREATE_SQL = "INSERT INTO users (email, password, role, created_at, updated_at) VALUES (?, ?, ?, ?, ?)";
    private final String FIND_BY_ID_SQL = "SELECT * FROM users WHERE id = ?";
    private final String UPDATE_SQL = "UPDATE users SET email = ?, password = ?, role = ?, updated_at = ? WHERE id = ?";
    private final String DELETE_BY_ID_FROM_USERS_SQL = "DELETE FROM users WHERE id = ?";
    private final String DELETE_BY_ID_FROM_ACCOUNTS_SQL = "DELETE FROM accounts WHERE user_id = ?";
    private final String INACTIVE_COMMENT_SQL = "UPDATE comments SET is_active = false WHERE user_id = ?";
    private final String DELETE_USER_MOVIE_SQL = "DELETE FROM user_movie WHERE account_id = ?";

    public UserRepository(DataSource ds) {
        this.ds = ds;
    }

    public User create(User user) {
        try (Connection conn = ds.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(CREATE_SQL);
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
        try (Connection conn = ds.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(FIND_BY_ID_SQL);
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
        try (Connection conn = ds.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(UPDATE_SQL);
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
        try (Connection conn = ds.getConnection()) {
            PreparedStatement psComments = conn.prepareStatement(INACTIVE_COMMENT_SQL);
            psComments.setInt(1, id);
            psComments.executeUpdate();

            PreparedStatement psUserMovie = conn.prepareStatement(DELETE_USER_MOVIE_SQL);
            psUserMovie.setInt(1, id);
            psUserMovie.executeUpdate();

            PreparedStatement psAccounts = conn.prepareStatement(DELETE_BY_ID_FROM_ACCOUNTS_SQL);
            psAccounts.setInt(1, id);
            psAccounts.executeUpdate();

            PreparedStatement psUsers = conn.prepareStatement(DELETE_BY_ID_FROM_USERS_SQL);
            psUsers.setInt(1, id);
            psUsers.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
