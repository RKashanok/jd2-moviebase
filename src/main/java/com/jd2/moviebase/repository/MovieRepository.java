package com.jd2.moviebase.repository;

import com.jd2.moviebase.model.Movie;
import java.sql.Array;
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
public class MovieRepository {

    private static final String CREATE_SQL = "INSERT INTO movies " +
        "(tmdb_id, name, genre_id, release_date, rating, overview, original_language) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String FIND_BY_ID_SQL = "SELECT * FROM movies WHERE id = ?";
    private static final String FIND_SQL = "SELECT * FROM movies";
    private static final String UPDATE_SQL = "UPDATE movies SET tmdb_id = ?, name = ?, genre_id = ?, " +
        "release_date = ?, rating = ?, overview = ?, original_language = ? WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM movies WHERE id = ?";

    private final DataSource dataSource;

    @Autowired
    public MovieRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Movie> findAll() {
        List<Movie> movies = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(FIND_SQL)) {
            while (rs.next()) {
                movies.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while finding all movies", e);
        }
        return movies;
    }

    public Optional<Movie> findById(int id) {
        try (Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(FIND_BY_ID_SQL)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRow(rs));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while fetching movie by ID", e);
        }
    }

    public Movie create(Movie movie) {
        try (Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(CREATE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, movie.getTmdbId());
            ps.setString(2, movie.getName());
            ps.setArray(3, conn.createArrayOf("integer", movie.getGenreId().toArray()));
            ps.setDate(4, movie.getReleaseDate());
            ps.setInt(5, movie.getRating());
            ps.setString(6, movie.getOverview());
            ps.setString(7, movie.getOriginalLanguage());
            ps.executeUpdate();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    movie.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating movie failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while creating movie", e);
        }
        return movie;
    }

    public Movie update(Movie movie) {
        try (Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {
            ps.setInt(1, movie.getTmdbId());
            ps.setString(2, movie.getName());
            ps.setArray(3, conn.createArrayOf("integer", movie.getGenreId().toArray()));
            ps.setDate(4, movie.getReleaseDate());
            ps.setInt(5, movie.getRating());
            ps.setString(6, movie.getOverview());
            ps.setString(7, movie.getOriginalLanguage());
            ps.setInt(8, movie.getId());
            if (ps.executeUpdate() > 0) {
                return movie;
            } else {
                throw new SQLException("Updating movie failed, no rows affected. Movie ID: " + movie.getId());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteById(int id) {
        try (Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(DELETE_SQL)) {
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new SQLException("Deleting movie failed. Genre ID: " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting genre", e);
        }

    }

    private Movie mapRow(ResultSet rs) throws SQLException {
        return Movie.builder()
            .id(rs.getInt("id"))
            .tmdbId(rs.getInt("tmdb_id"))
            .name(rs.getString("name"))
            .genreId(getGenres(rs))
            .releaseDate(rs.getDate("release_date"))
            .rating(rs.getInt("rating"))
            .overview(rs.getString("overview"))
            .originalLanguage(rs.getString("original_language"))
            .build();
    }

    private List<Integer> getGenres(ResultSet rs) throws SQLException {
        Array sqlArray = rs.getArray("genre_id");
        if (sqlArray != null) {
            Integer[] genreIds = (Integer[]) sqlArray.getArray();
            return List.of(genreIds);
        } else {
            return List.of();
        }
    }
}
