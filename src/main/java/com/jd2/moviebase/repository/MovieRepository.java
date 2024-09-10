package com.jd2.moviebase.repository;

import com.jd2.moviebase.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

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
            throw new RuntimeException(e);
        }
        return movies;
    }

    public Optional<Movie> findById(int id) {

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_BY_ID_SQL)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public Movie create(Movie movie) {

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(CREATE_SQL, Statement.RETURN_GENERATED_KEYS)){

            composeStatement(ps, movie, conn);
            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    movie.setId(generatedKeys.getInt("id"));
                    return movie;
                } else {
                    throw new SQLException("Creating movie failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Movie update(Movie movie) {

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)){

            composeStatement(ps, movie, conn);
            ps.setInt(8, movie.getId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Updating movie failed, no rows affected.");
            }

        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
        return movie;
    }

    public void deleteById(int id) {

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_SQL)) {

            ps.setInt(1, id);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Deleting movie failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private Movie mapRow(ResultSet rs) throws SQLException {
        Movie movie = new Movie();
        movie.setId(rs.getInt("id"));
        movie.setId(rs.getInt("tmdb_id"));
        movie.setName(rs.getString("name"));

        Array sqlArray = rs.getArray("genre_id");
        if (sqlArray != null) {
            Integer[] genreIds = (Integer[]) sqlArray.getArray();
            movie.setGenreId(List.of(genreIds));
        } else {
            movie.setGenreId(new ArrayList<Integer>());
        }

        movie.setReleaseDate(rs.getDate("release_date"));
        movie.setRating(rs.getInt("rating"));
        movie.setOverview(rs.getString("overview"));
        movie.setOriginalLanguage(rs.getString("original_language"));

        return movie;

    }

    private void composeStatement(PreparedStatement ps, Movie movie, Connection conn) throws SQLException {
        ps.setInt(1, movie.getTmdbId());
        ps.setString(2, movie.getName());
        ps.setArray(3, conn.createArrayOf("integer", movie.getGenreId().toArray()));
        ps.setDate(4, movie.getReleaseDate());
        ps.setInt(5, movie.getRating());
        ps.setString(6, movie.getOverview());
        ps.setString(7, movie.getOriginalLanguage());

    }

}
