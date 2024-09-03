package com.jd2.moviebase.repository;

import com.jd2.moviebase.config.DataSource;
import com.jd2.moviebase.model.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class GenreRepository {

    private static final String CREATE_SQL = "INSERT INTO genres (tmdb_id, name) VALUES (?, ?)";
    private static final String FIND_BY_ID_SQL = "SELECT * FROM genres WHERE id = ?";
    private static final String FIND_SQL = "SELECT * FROM genres";
    private static final String UPDATE_SQL = "UPDATE genres SET tmdb_id = ?, name = ? WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM genres WHERE id = ?";

    private final DataSource dataSource;

    @Autowired
    public GenreRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Genre> findAll() {
        List<Genre> genres = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(FIND_SQL)) {

            while (rs.next()) {
                genres.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return genres;
    }

    public Optional<Genre> findById(int id) {

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_BY_ID_SQL)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    public Genre create(Genre genre) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(CREATE_SQL, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, genre.getTmdbId());
            ps.setString(2, genre.getName());
            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if(generatedKeys.next()) {
                    genre.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating genre failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return genre;
    }

    public Genre update(Genre genre) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {

            ps.setInt(1, genre.getTmdbId());
            ps.setString(2, genre.getName());
            ps.setInt(3, genre.getId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Updating genre failed, no rows affected.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return genre;
    }

    public void deleteById(int id) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_SQL)) {

            ps.setInt(1, id);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Deleting genre failed, no rows affected.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Genre mapRow(ResultSet rs) throws SQLException {
        Genre genre = new Genre();
        genre.setId(rs.getInt("id"));
        genre.setTmdbId(rs.getInt("tmdb_id"));
        genre.setName(rs.getString("name"));

        return genre;
    }

}
