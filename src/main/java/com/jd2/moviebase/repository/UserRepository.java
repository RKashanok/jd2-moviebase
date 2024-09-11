package com.jd2.moviebase.repository;

import com.jd2.moviebase.config.DataSource;
import com.jd2.moviebase.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

@Repository
public class UserRepository {
    private final DataSource ds;
    private final String CREATE_SQL = "INSERT INTO users (email, password, role) VALUES (?, ?, ?)";
    private final String FIND_BY_ID_SQL = "SELECT * FROM users WHERE id = ?";
    private final String UPDATE_SQL = "UPDATE users SET email = ?, password = ?, role = ?, updated_at = ? WHERE id = ?";
    private final String DELETE_BY_ID_FROM_USERS_SQL = "DELETE FROM users WHERE id = ?";

    @Autowired
    public UserRepository(DataSource ds) {
        this.ds = ds;
    }

    public User create(User user) {
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(CREATE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            int insertedId;
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole());
            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                insertedId = generatedKeys.getInt(1);
                user.setId(insertedId);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public User findById(int id) {
        User user = null;
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_BY_ID_SQL)) {
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                user = new User();
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
        return Optional.ofNullable(user)
                .orElseThrow(() -> new RuntimeException("User with ID " + id + " not found"));
    }

    public User update(User user) {
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {
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
        try (Connection conn = ds.getConnection();
             PreparedStatement psUsers = conn.prepareStatement(DELETE_BY_ID_FROM_USERS_SQL)) {
            psUsers.setInt(1, id);
            psUsers.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
