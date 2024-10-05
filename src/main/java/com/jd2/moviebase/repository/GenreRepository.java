package com.jd2.moviebase.repository;

import com.jd2.moviebase.model.Genre;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
                genres.add(createGenre(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while finding all genres", e);
        }
        return genres;
    }

    public Optional<Genre> findById(int id) {
        try (Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(FIND_BY_ID_SQL)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(createGenre(rs));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while fetching genre by ID", e);
        }
    }

    public Genre create(Genre genre) {
        try (Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(CREATE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, genre.getTmdbId());
            ps.setString(2, genre.getName());
            ps.executeUpdate();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    genre.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating genre failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while creating genre", e);
        }
        return genre;
    }

    public Genre update(Genre genre) {
        try (Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {
            ps.setLong(1, genre.getTmdbId());
            ps.setString(2, genre.getName());
            ps.setLong(3, genre.getId());
            if (ps.executeUpdate() > 0) {
                return genre;
            } else {
                throw new RuntimeException("Updating genre failed, no rows affected. Genre ID: " + genre.getId());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating genre", e);
        }
    }

    public void deleteById(Long id) {
        try (Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(DELETE_SQL)) {
            ps.setLong(1, id);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new SQLException("Deleting genre failed. Genre ID: " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting genre", e);
        }
    }

    private Genre createGenre(ResultSet rs) throws SQLException {
        return Genre.builder()
            .id(rs.getLong("id"))
            .tmdbId(rs.getLong("tmdb_id"))
            .name(rs.getString("name"))
            .build();
    }

}
