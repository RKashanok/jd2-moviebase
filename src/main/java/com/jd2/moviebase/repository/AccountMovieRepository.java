package com.jd2.moviebase.repository;

import com.jd2.moviebase.exception.MovieDbRepositoryOperationException;
import com.jd2.moviebase.model.AccountMovie;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static com.jd2.moviebase.util.ConstantsHelper.MovieStatus;

@Repository
public class AccountMovieRepository {

    private final DataSource ds;
    private final String CREATE_ACC_MOVIE_SQL = "INSERT INTO account_movie (account_id, movie_id, status) VALUES (?, ?, ?)";
    private final String DELETE_ACC_MOVIE_BY_ACC_ID_SQL = "DELETE FROM account_movie WHERE account_id = ?";
    private final String FIND_ALL_ACC_MOVIE_BY_ACC_ID_SQL = "SELECT * FROM account_movie WHERE account_id = ?";
    private final String UPDATE_ACC_MOVIE_STATUS_BY_ACC_ID_SQL = "UPDATE account_movie SET status = ? WHERE account_id = ? AND movie_id = ?";

    public AccountMovieRepository(DataSource ds) {
        this.ds = ds;
    }

    public void create(AccountMovie accountMovie) {
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(CREATE_ACC_MOVIE_SQL)) {
            ps.setLong(1, accountMovie.getAccountId());
            ps.setLong(2, accountMovie.getMovieId());
            ps.setString(3, accountMovie.getStatus());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new MovieDbRepositoryOperationException("Error creating account movies", e, "test details");
        }
    }

    public List<AccountMovie> findAllByAccountId(Long accountId) {
        List<AccountMovie> accountMovies = new ArrayList<>();
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_ALL_ACC_MOVIE_BY_ACC_ID_SQL)) {
            ps.setLong(1, accountId);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                accountMovies.add(mapRow(resultSet));
            }
        } catch (SQLException e) {
            throw new MovieDbRepositoryOperationException("Error getting account movies", e);
        }

        return accountMovies;
    }

    public void updateStatusByAccId(Long accountId, Long movie_id, MovieStatus status) {
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_ACC_MOVIE_STATUS_BY_ACC_ID_SQL)) {
            ps.setString(1, String.valueOf(status));
            ps.setLong(2, accountId);
            ps.setLong(3, movie_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new MovieDbRepositoryOperationException("Error updating account movies ", e);
        }
    }

    public void deleteByAccId(Long id) {
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_ACC_MOVIE_BY_ACC_ID_SQL)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new MovieDbRepositoryOperationException("Error deleting account movies ", e);
        }
    }

    private AccountMovie mapRow(ResultSet resultSet) throws SQLException {
        return AccountMovie.builder()
                .accountId(resultSet.getLong("account_id"))
                .movieId(resultSet.getLong("movie_id"))
                .status(resultSet.getString("status"))
                .createdAt(LocalDateTime.from(resultSet.getTimestamp("created_at").toLocalDateTime()
                        .atZone(ZoneId.of("UTC"))))
                .updatedAt(LocalDateTime.from(resultSet.getTimestamp("created_at").toLocalDateTime()
                        .atZone(ZoneId.of("UTC"))))
                .build();
    }
}
