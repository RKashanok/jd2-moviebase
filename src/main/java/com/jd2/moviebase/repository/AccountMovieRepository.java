package com.jd2.moviebase.repository;

import com.jd2.moviebase.config.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AccountMovieRepository {
    private final DataSource ds;

    public AccountMovieRepository(DataSource ds) {
        this.ds = ds;
    }

    private final String DELETE_USER_MOVIE_BY_ACC_ID_SQL = "DELETE FROM account_movie WHERE account_id = ?";

    public void deleteByAccId(int id) {
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_USER_MOVIE_BY_ACC_ID_SQL)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
