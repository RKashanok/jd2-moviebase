package com.jd2.moviebase.repository;

import com.jd2.moviebase.exception.MovieDbRepositoryOperationException;
import com.jd2.moviebase.model.Account;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;

@Repository
public class AccountRepository {

    private final DataSource ds;
    private final String CREATE_SQL =
        "INSERT INTO accounts (user_id, first_name, last_name, preferred_name, date_of_birth, " +
            "phone, gender, photo_url, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String FIND_BY_ID_SQL = "SELECT * FROM accounts WHERE id = ?";
    private final String FIND_BY_USER_ID_SQL = "SELECT * FROM accounts WHERE user_id = ?";
    private final String UPDATE_SQL =
        "UPDATE accounts SET user_id = ?, first_name = ?, last_name = ?, preferred_name = ?, " +
            "date_of_birth = ?, phone = ?, gender = ?, photo_url = ?, updated_at = ? WHERE id = ?";
    private final String DELETE_BY_ID_FROM_ACCOUNTS_SQL = "DELETE FROM accounts WHERE id = ?";

    public AccountRepository(DataSource ds) {
        this.ds = ds;
    }

    public Account create(Account account) {
        Long insertedId = 0L;
        try (Connection conn = ds.getConnection();
            PreparedStatement ps = conn.prepareStatement(CREATE_SQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, account.getUserId());
            ps.setString(2, account.getFirstName());
            ps.setString(3, account.getLastName());
            ps.setString(4, account.getPreferredName());
            ps.setDate(5, java.sql.Date.valueOf(account.getDateOfBirth()));
            ps.setString(6, account.getPhone());
            ps.setString(7, account.getGender());
            ps.setString(8, account.getPhotoUrl());
            ps.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now(ZoneId.of("UTC"))));
            ps.setTimestamp(10, Timestamp.valueOf(LocalDateTime.now(ZoneId.of("UTC"))));
            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                insertedId = generatedKeys.getLong(1);
                account.setId(insertedId);
            } else {
                throw new SQLException("Creating account failed, no ID obtained.");
            }
        } catch (SQLException e) {
            throw new MovieDbRepositoryOperationException("Error creating account", e);
        }
        return account;
    }

    public Account findById(Long id) {
        Account account = null;
        try (Connection conn = ds.getConnection();
            PreparedStatement ps = conn.prepareStatement(FIND_BY_ID_SQL)) {
            ps.setLong(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                account = getAccountObject(resultSet);
            }
        } catch (SQLException e) {
            throw new MovieDbRepositoryOperationException("Error while fetching account by ID", e);
        }
        return Optional.ofNullable(account)
            .orElseThrow(() -> new MovieDbRepositoryOperationException("Account with ID " + id + " not found"));
    }

    public Account findByUserId(Long userId) {
        try (Connection conn = ds.getConnection();
            PreparedStatement ps = conn.prepareStatement(FIND_BY_USER_ID_SQL)) {
            ps.setLong(1, userId);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return getAccountObject(resultSet);
            } else {
                throw new SQLException("Account with user ID " + userId + " not found");
            }
        } catch (SQLException e) {
            throw new MovieDbRepositoryOperationException("Error getting account", e);
        }
    }

    public Account update(Account account) {
        account.setUpdatedAt(LocalDateTime.now(ZoneId.of("UTC")));
        try (Connection conn = ds.getConnection();
            PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {
            ps.setLong(1, account.getUserId());
            ps.setString(2, account.getFirstName());
            ps.setString(3, account.getLastName());
            ps.setString(4, account.getPreferredName());
            ps.setDate(5, java.sql.Date.valueOf(account.getDateOfBirth()));
            ps.setString(6, account.getPhone());
            ps.setString(7, account.getGender());
            ps.setString(8, account.getPhotoUrl());
            ps.setTimestamp(9, Timestamp.valueOf(account.getUpdatedAt()));
            ps.setLong(10, account.getId());
            if (ps.executeUpdate() > 0) {
                return account;
            } else {
                throw new SQLException("Updating account failed, no rows affected. Account ID: " + account.getId());
            }
        } catch (SQLException e) {
            throw new MovieDbRepositoryOperationException("Error updating account ", e);
        }
    }

    public void deleteById(Long id) {
        try (Connection conn = ds.getConnection();
            PreparedStatement psAccounts = conn.prepareStatement(DELETE_BY_ID_FROM_ACCOUNTS_SQL)) {
            psAccounts.setLong(1, id);
            psAccounts.executeUpdate();
        } catch (SQLException e) {
            throw new MovieDbRepositoryOperationException("Error deleting account", e);
        }
    }

    private Account getAccountObject(ResultSet resultSet) throws SQLException {
        return Account.builder()
            .id(resultSet.getLong("id"))
            .userId(resultSet.getLong("user_id"))
            .firstName(resultSet.getString("first_name"))
            .lastName(resultSet.getString("last_name"))
            .preferredName(resultSet.getString("preferred_name"))
            .dateOfBirth(resultSet.getDate("date_of_birth").toLocalDate())
            .phone(resultSet.getString("phone"))
            .gender(resultSet.getString("gender"))
            .photoUrl(resultSet.getString("photo_url"))
            .createdAt(LocalDateTime.from(resultSet.getTimestamp("created_at").toLocalDateTime()
                .atZone(ZoneId.of("UTC"))))
            .updatedAt(
                LocalDateTime.from(resultSet.getTimestamp("updated_at").toLocalDateTime().atZone(ZoneId.of("UTC"))))
            .build();
    }
}
