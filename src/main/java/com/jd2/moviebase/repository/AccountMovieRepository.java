package com.jd2.moviebase.repository;

import static com.jd2.moviebase.util.ConstantsHelper.MovieStatus;

import com.jd2.moviebase.model.AccountMovie;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;

@Repository
public class AccountMovieRepository {

    private final DataSource ds;

    public AccountMovieRepository(DataSource ds) {
        this.ds = ds;
    }

    private final String CREATE_ACC_MOVIE_SQL = "INSERT INTO account_movie (account_id, movie_id, status) VALUES (?, ?, ?)";
    private final String DELETE_ACC_MOVIE_BY_ACC_ID_SQL = "DELETE FROM account_movie WHERE account_id = ?";
    private final String FIND_ALL_ACC_MOVIE_BY_ACC_ID_SQL = "SELECT * FROM account_movie WHERE account_id = ?";
    private final String UPDATE_ACC_MOVIE_STATUS_BY_ACC_ID_SQL = "UPDATE account_movie SET status = ? WHERE account_id = ? AND movie_id = ?";

    public void create(AccountMovie accountMovie) {
        try (Connection conn = ds.getConnection();
            PreparedStatement ps = conn.prepareStatement(CREATE_ACC_MOVIE_SQL)) {
            ps.setInt(1, accountMovie.getAccountId());
            ps.setInt(2, accountMovie.getMovieId());
            ps.setString(3, accountMovie.getStatus());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<AccountMovie> findAllByAccountId(int accountId) {
        List<AccountMovie> accountMovies = new ArrayList<>();
        try (Connection conn = ds.getConnection();
            PreparedStatement ps = conn.prepareStatement(FIND_ALL_ACC_MOVIE_BY_ACC_ID_SQL)) {
            ps.setInt(1, accountId);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                accountMovies.add(getAccountMovieObject(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting account movies", e);
        }

        return accountMovies;
    }

    public void updateStatusByAccId(int accountId, int movie_id, MovieStatus status) {
        try (Connection conn = ds.getConnection();
            PreparedStatement ps = conn.prepareStatement(UPDATE_ACC_MOVIE_STATUS_BY_ACC_ID_SQL)) {
            ps.setString(1, String.valueOf(status));
            ps.setInt(2, accountId);
            ps.setInt(3, movie_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteByAccId(int id) {
        try (Connection conn = ds.getConnection();
            PreparedStatement ps = conn.prepareStatement(DELETE_ACC_MOVIE_BY_ACC_ID_SQL)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private AccountMovie getAccountMovieObject(ResultSet resultSet) {
        try {
            return AccountMovie.builder()
                .accountId(resultSet.getInt("account_id"))
                .movieId(resultSet.getInt("movie_id"))
                .status(resultSet.getString("status"))
                .createdAt(resultSet.getDate("created_at"))
                .updatedAt(resultSet.getDate("updated_at"))
                .build();
        } catch (SQLException e) {
            throw new RuntimeException("Error creating AccountMovie object", e);
        }
    }
}
