package com.jd2.moviebase.repository;

import com.jd2.moviebase.config.DataSource;
import com.jd2.moviebase.model.Genre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GenreRepository {

    private static final String CREATE_SQL = "INSERT INTO genres (tmdbId, name) VALUES (?, ?)";
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
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(FIND_SQL)) {

            while (rs.next()) {
                genres.add(sortRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return genres;
    }

    public Genre findById(Genre genre) {
        int id = genre.getId();


        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_BY_ID_SQL)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    genre = sortRow(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return genre;
    }

    public Genre create(Genre genre) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(CREATE_SQL)) {

            ps.setInt(1, genre.getTmdbId());
            ps.setString(2, genre.getName());
            ps.executeUpdate();
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
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return genre;
    }

    public Genre deleteById(Genre genre) {
        int id = genre.getId();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(DELETE_SQL)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return findById(genre);
    }

    private Genre sortRow(ResultSet rs) throws SQLException {
        Genre genre = new Genre();
        genre.setId(rs.getInt("id"));
        genre.setTmdbId(rs.getInt("tmdb_id"));
        genre.setName(rs.getString("name"));

        return genre;
    }

}
