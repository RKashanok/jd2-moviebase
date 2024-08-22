package com.jd2.moviebase.repository;

import com.jd2.moviebase.config.DataSource;
import com.jd2.moviebase.model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    public Account create(Account account) {
        try (Connection conn = ds.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(CREATE_SQL);
            ps.setInt(1, account.getUserId());
            ps.setString(2, account.getFirstName());
            ps.setString(3, account.getLastName());
            ps.setString(4, account.getPreferredName());
            ps.setDate(5, new java.sql.Date(account.getDateOfBirth().getTime()));
            ps.setString(6, account.getPhone());
            ps.setString(7, account.getGender());
            ps.setString(8, account.getPhotoUrl());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return account;
    }

    public Account findById(int id) {
        Account account = new Account();
        try (Connection conn = ds.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(FIND_BY_ID_SQL);
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
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
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return account;
    }

    public Account findByUserId(int userId) {
        Account account = new Account();
        try (Connection conn = ds.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(FIND_BY_USER_ID_SQL);
            ps.setInt(1, userId);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
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
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return account;
    }

    public Account update(Account account) {
        try (Connection conn = ds.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(UPDATE_SQL);
            ps.setInt(1, account.getUserId());
            ps.setString(2, account.getFirstName());
            ps.setString(3, account.getLastName());
            ps.setString(4, account.getPreferredName());
            ps.setDate(5, new java.sql.Date(account.getDateOfBirth().getTime()));
            ps.setString(6, account.getPhone());
            ps.setString(7, account.getGender());
            ps.setString(8, account.getPhotoUrl());
            ps.setTimestamp(9, new java.sql.Timestamp(account.getUpdatedAt().getTime()));
            ps.setInt(10, account.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return account;
    }

    public void deleteById(int id) {
        try (Connection conn = ds.getConnection()) {
            PreparedStatement psAccounts = conn.prepareStatement(DELETE_BY_ID_FROM_ACCOUNTS_SQL);
            psAccounts.setInt(1, id);
            psAccounts.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
