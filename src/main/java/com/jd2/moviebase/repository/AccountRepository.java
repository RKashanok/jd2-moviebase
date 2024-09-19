package com.jd2.moviebase.repository;

import com.jd2.moviebase.dto.AccountDto;
import com.jd2.moviebase.model.Account;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class AccountRepository {
    private final DataSource ds;
    private final String CREATE_SQL = "INSERT INTO accounts (user_id, first_name, last_name, preferred_name, date_of_birth, " +
            "phone, gender, photo_url) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private final String FIND_BY_ID_SQL = "SELECT * FROM accounts WHERE id = ?";
    private final String FIND_BY_USER_ID_SQL = "SELECT * FROM accounts WHERE user_id = ?";
    private final String UPDATE_SQL = "UPDATE accounts SET first_name = ?, last_name = ?, preferred_name = ?, " +
            "date_of_birth = ?, phone = ?, gender = ?, photo_url = ?, updated_at = ? WHERE id = ?";
    private final String DELETE_BY_ID_FROM_ACCOUNTS_SQL = "DELETE FROM accounts WHERE id = ?";

    public AccountRepository(DataSource ds) {
        this.ds = ds;
    }

    public Account create(Account account) {
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(CREATE_SQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            int insertedId;
            ps.setInt(1, account.getUserId());
            ps.setString(2, account.getFirstName());
            ps.setString(3, account.getLastName());
            ps.setString(4, account.getPreferredName());
            ps.setDate(5, new java.sql.Date(account.getDateOfBirth().getTime()));
            ps.setString(6, account.getPhone());
            ps.setString(7, account.getGender());
            ps.setString(8, account.getPhotoUrl());
            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                insertedId = generatedKeys.getInt(1);
                account.setId(insertedId);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return account;
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

    public AccountDto update(int id, AccountDto accountDto) {
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {
            ps.setString(1, accountDto.getFirstName());
            ps.setString(2, accountDto.getLastName());
            ps.setString(3, accountDto.getPreferredName());
            ps.setDate(4, new java.sql.Date(accountDto.getDateOfBirth().getTime()));
            ps.setString(5, accountDto.getPhone());
            ps.setString(6, accountDto.getGender());
            ps.setString(7, accountDto.getPhotoUrl());
            ps.setDate(8, new java.sql.Date(System.currentTimeMillis()));
            ps.setInt(9, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new AccountDto(accountDto.getFirstName(), accountDto.getLastName(), accountDto.getPreferredName(),
                accountDto.getDateOfBirth(), accountDto.getPhone(), accountDto.getGender(), accountDto.getPhotoUrl());
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
        Account account = new Account();
        account.setId(resultSet.getInt("id"));
        account.setUserId(resultSet.getInt("user_id"));
        account.setFirstName(resultSet.getString("first_name"));
        account.setLastName(resultSet.getString("last_name"));
        account.setPreferredName(resultSet.getString("preferred_name"));
        account.setDateOfBirth(resultSet.getDate("date_of_birth"));
        account.setPhone(resultSet.getString("phone"));
        account.setGender(resultSet.getString("gender"));
        account.setPhotoUrl(resultSet.getString("photo_url"));
        account.setCreatedAt(resultSet.getDate("created_at"));
        account.setUpdatedAt(resultSet.getDate("updated_at"));
        return account;
    }
}
