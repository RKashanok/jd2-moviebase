package com.jd2.moviebase.repository;

import com.jd2.moviebase.exception.MovieDbRepositoryOperationException;
import com.jd2.moviebase.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private final DataSource ds;
    private final String CREATE_SQL = "INSERT INTO users (email, password, role, created_at, updated_at) VALUES (?, ?, ?, ?, ?)";
    private final String FIND_BY_ID_SQL = "SELECT * FROM users WHERE id = ?";
    private final String FIND_ALL_SQL = "SELECT * FROM users";
    private final String UPDATE_SQL = "UPDATE users SET email = ?, password = ?, role = ?, updated_at = ? WHERE id = ?";
    private final String DELETE_BY_ID_FROM_USERS_SQL = "DELETE FROM users WHERE id = ?";

    @Autowired
    public UserRepository(DataSource ds) {
        this.ds = ds;
    }

    public User create(User user) {
        try (Connection conn = ds.getConnection();
            PreparedStatement ps = conn.prepareStatement(CREATE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole());
            ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now(ZoneId.of("UTC"))));
            ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now(ZoneId.of("UTC"))));
            ps.executeUpdate();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new MovieDbRepositoryOperationException("Error while creating user", e);
        }
        return user;
    }

    public Optional<User> findById(Long id) {
        try (Connection conn = ds.getConnection();
            PreparedStatement ps = conn.prepareStatement(FIND_BY_ID_SQL)) {
            ps.setLong(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return Optional.of(createUser(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new MovieDbRepositoryOperationException("Error while fetching user by ID " + id, e);
        }
    }

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection conn = ds.getConnection();
            Statement st = conn.createStatement()) {
            ResultSet resultSet = st.executeQuery(FIND_ALL_SQL);
            while (resultSet.next()) {
                users.add(createUser(resultSet));
            }
        } catch (SQLException e) {
            throw new MovieDbRepositoryOperationException("Error while finding all users", e);
        }
        return users;
    }

    public User update(User user) {
        user.setUpdatedAt(LocalDateTime.now(ZoneId.of("UTC")));
        try (Connection conn = ds.getConnection();
            PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole());
            ps.setTimestamp(4, Timestamp.valueOf(user.getUpdatedAt()));
            ps.setLong(5, user.getId());
            if (ps.executeUpdate() > 0) {
                return user;
            } else {
                throw new SQLException("Updating user failed, no rows affected. User ID: " + user.getId());
            }
        } catch (SQLException e) {
            throw new MovieDbRepositoryOperationException("Error updating account ", e);
        }
    }

    public void deleteById(Long id) {
        try (Connection conn = ds.getConnection();
            PreparedStatement psUsers = conn.prepareStatement(DELETE_BY_ID_FROM_USERS_SQL)) {
            psUsers.setLong(1, id);
            psUsers.executeUpdate();
        } catch (SQLException e) {
            throw new MovieDbRepositoryOperationException("Error deleting user", e);
        }
    }

    private User createUser(ResultSet resultSet) throws SQLException {
        return User.builder()
            .id(resultSet.getLong("id"))
            .email(resultSet.getString("email"))
            .password(resultSet.getString("password"))
            .role(resultSet.getString("role"))
            .createdAt(LocalDateTime.from(resultSet.getTimestamp("created_at").toLocalDateTime()
                .atZone(ZoneId.of("UTC"))))
            .updatedAt(
                LocalDateTime.from(resultSet.getTimestamp("updated_at").toLocalDateTime().atZone(ZoneId.of("UTC"))))
            .build();
    }
}
