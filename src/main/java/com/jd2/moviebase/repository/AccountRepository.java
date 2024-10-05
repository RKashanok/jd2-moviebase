package com.jd2.moviebase.repository;

import com.jd2.moviebase.dto.AccountDto;
import com.jd2.moviebase.model.Account;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;

@Repository
public class AccountRepository {
    private final DataSource ds;
    private final String CREATE_SQL = "INSERT INTO accounts (user_id, first_name, last_name, preferred_name, date_of_birth, " +
            "phone, gender, photo_url) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private final String FIND_BY_ID_SQL = "SELECT * FROM accounts WHERE id = ?";
    private final String FIND_BY_USER_ID_SQL = "SELECT * FROM accounts WHERE user_id = ?";
    private final String UPDATE_SQL = "UPDATE accounts SET user_id = ?, first_name = ?, last_name = ?, preferred_name = ?, " +
            "date_of_birth = ?, phone = ?, gender = ?, photo_url = ?, updated_at = ? WHERE id = ?";
    private final String DELETE_BY_ID_FROM_ACCOUNTS_SQL = "DELETE FROM accounts WHERE id = ?";

    public AccountRepository(DataSource ds) {
        this.ds = ds;
    }

    public Account create(AccountDto accountDto) {
        int insertedId = 0;
        Date createdAt = null;
        Date updatedAt = null;
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(CREATE_SQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, accountDto.getUserId());
            ps.setString(2, accountDto.getFirstName());
            ps.setString(3, accountDto.getLastName());
            ps.setString(4, accountDto.getPreferredName());
            ps.setDate(5, new java.sql.Date(accountDto.getDateOfBirth().getTime()));
            ps.setString(6, accountDto.getPhone());
            ps.setString(7, accountDto.getGender());
            ps.setString(8, accountDto.getPhotoUrl());
            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                insertedId = generatedKeys.getInt(1);
                createdAt = generatedKeys.getDate(10);
                updatedAt = generatedKeys.getDate(11);
                accountDto.setId(insertedId);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new Account(insertedId, accountDto.getUserId(), accountDto.getFirstName(), accountDto.getLastName(),
                accountDto.getPreferredName(), accountDto.getDateOfBirth(), accountDto.getPhone(),
                accountDto.getGender(), accountDto.getPhotoUrl(), createdAt, updatedAt);
    }

    public Account findById(int id) {
        Account account = null;
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_BY_ID_SQL)) {
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                account = getAccountObject(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(account)
                .orElseThrow(() -> new RuntimeException("Account with ID " + id + " not found"));
    }

    public Account findByUserId(int userId) {
        Account account = null;
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_BY_USER_ID_SQL)) {
            ps.setInt(1, userId);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                account = getAccountObject(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(account)
                .orElseThrow(() -> new RuntimeException("Account with user ID " + userId + " not found"));
    }

    public Account update(int id, AccountDto accountDto) {
        Date createdAt = null;
        Date updatedAt = null;
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {
            ps.setInt(1, accountDto.getUserId());
            ps.setString(2, accountDto.getFirstName());
            ps.setString(3, accountDto.getLastName());
            ps.setString(4, accountDto.getPreferredName());
            ps.setDate(5, new java.sql.Date(accountDto.getDateOfBirth().getTime()));
            ps.setString(6, accountDto.getPhone());
            ps.setString(7, accountDto.getGender());
            ps.setString(8, accountDto.getPhotoUrl());
            ps.setDate(9, new java.sql.Date(System.currentTimeMillis()));
            ps.setInt(10, id);
            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                createdAt = generatedKeys.getDate(10);
                updatedAt = generatedKeys.getDate(11);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new Account(id, accountDto.getUserId(), accountDto.getFirstName(), accountDto.getLastName(),
                accountDto.getPreferredName(), accountDto.getDateOfBirth(), accountDto.getPhone(),
                accountDto.getGender(), accountDto.getPhotoUrl(), createdAt, updatedAt);
    }

    public void deleteById(int id) {
        try (Connection conn = ds.getConnection();
             PreparedStatement psAccounts = conn.prepareStatement(DELETE_BY_ID_FROM_ACCOUNTS_SQL)) {
            psAccounts.setInt(1, id);
            psAccounts.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Account getAccountObject(ResultSet resultSet) throws SQLException {
        return new Account(
                resultSet.getInt("id"),
                resultSet.getInt("user_id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("preferred_name"),
                resultSet.getDate("date_of_birth"),
                resultSet.getString("phone"),
                resultSet.getString("gender"),
                resultSet.getString("photo_url"),
                resultSet.getDate("created_at"),
                resultSet.getDate("updated_at")
        );
    }
}
