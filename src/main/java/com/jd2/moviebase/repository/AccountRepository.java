package com.jd2.moviebase.repository;

import com.jd2.moviebase.dto.AccountDto;
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
    private final String UPDATE_SQL = "UPDATE accounts SET user_id = ?, first_name = ?, last_name = ?, preferred_name = ?, " +
            "date_of_birth = ?, phone = ?, gender = ?, photo_url = ?, updated_at = ? WHERE id = ?";
    private final String DELETE_BY_ID_FROM_ACCOUNTS_SQL = "DELETE FROM accounts WHERE id = ?";

    public AccountRepository(DataSource ds) {
        this.ds = ds;
    }

    public AccountDto create(AccountDto accountDto) {
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(CREATE_SQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            int insertedId;
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
                accountDto.setId(insertedId);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return accountDto;
    }

    public AccountDto findById(int id) {
        AccountDto accountDto = null;
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_BY_ID_SQL)) {
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                accountDto = getAccountDtoObject(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(accountDto)
                .orElseThrow(() -> new RuntimeException("Account with ID " + id + " not found"));
    }

    public AccountDto findByUserId(int userId) {
        AccountDto accountDto = null;
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_BY_USER_ID_SQL)) {
            ps.setInt(1, userId);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                accountDto = getAccountDtoObject(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(accountDto)
                .orElseThrow(() -> new RuntimeException("Account with user ID " + userId + " not found"));
    }

    public AccountDto update(int id, AccountDto accountDto) {
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new AccountDto(id, accountDto.getUserId(), accountDto.getFirstName(), accountDto.getLastName(),
                accountDto.getPreferredName(), accountDto.getDateOfBirth(), accountDto.getPhone(),
                accountDto.getGender(), accountDto.getPhotoUrl());
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

    private AccountDto getAccountDtoObject(ResultSet resultSet) throws SQLException {
        AccountDto accountDto = new AccountDto();
        accountDto.setId(resultSet.getInt("id"));
        accountDto.setUserId(resultSet.getInt("user_id"));
        accountDto.setFirstName(resultSet.getString("first_name"));
        accountDto.setLastName(resultSet.getString("last_name"));
        accountDto.setPreferredName(resultSet.getString("preferred_name"));
        accountDto.setDateOfBirth(resultSet.getDate("date_of_birth"));
        accountDto.setPhone(resultSet.getString("phone"));
        accountDto.setGender(resultSet.getString("gender"));
        accountDto.setPhotoUrl(resultSet.getString("photo_url"));
        return accountDto;
    }
}
