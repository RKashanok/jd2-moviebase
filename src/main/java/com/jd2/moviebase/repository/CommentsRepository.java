package com.jd2.moviebase.repository;

import com.jd2.moviebase.config.DataSource;
import com.jd2.moviebase.model.Comment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommentsRepository {
    private final DataSource ds;

    public CommentsRepository(DataSource ds) {
        this.ds = ds;
    }

    private final String CREATE_SQL = "INSERT INTO comments (account_id, movie_id, note, is_active) VALUES (?, ?, ?, true)";
    private final String FIND_BY_ID_SQL = "SELECT * FROM comments WHERE id = ?";
    private final String FIND_BY_ACC_ID_SQL = "SELECT * FROM comments WHERE account_id = ?";
    private final String FIND_BY_MOVIE_ID_SQL = "SELECT * FROM comments WHERE movie_id = ?";
    private final String UPDATE_SQL = "UPDATE comments SET note = ?, updated_at = ?, is_active = ? WHERE id = ?";
    private final String DEACTIVE_COMMENT_BY_ACC_ID_SQL = "UPDATE comments SET is_active = false, account_id = NULL  WHERE account_id = ?";

    public Comment create(Comment comment) {
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(CREATE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            int insertedId;
            ps.setInt(1, comment.getAccountId());
            ps.setInt(2, comment.getMovieId());
            ps.setString(3, comment.getNote());
            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                insertedId = generatedKeys.getInt(1);
                comment.setId(insertedId);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return comment;
    }

    public Comment findById(int id) {
        Comment comment = null;
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_BY_ID_SQL)) {
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                comment = new Comment();
                comment.setId(resultSet.getInt("id"));
                comment.setAccountId(resultSet.getInt("account_id"));
                comment.setMovieId(resultSet.getInt("movie_id"));
                comment.setNote(resultSet.getString("note"));
                comment.setActive(resultSet.getBoolean("is_active"));
                comment.setCreatedAt(resultSet.getDate("created_at"));
                comment.setUpdatedAt(resultSet.getDate("updated_at"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(comment)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
    }

    public List<Comment> findAllByAcctId(int accountId) {
        List<Comment> comments = new ArrayList<>();

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_BY_ACC_ID_SQL)) {
            ps.setInt(1, accountId);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Comment comment = new Comment();
                comment.setId(resultSet.getInt("id"));
                comment.setAccountId(resultSet.getInt("account_id"));
                comment.setMovieId(resultSet.getInt("movie_id"));
                comment.setNote(resultSet.getString("note"));
                comment.setActive(resultSet.getBoolean("is_active"));
                comment.setCreatedAt(resultSet.getDate("created_at"));
                comment.setUpdatedAt(resultSet.getDate("updated_at"));
                comments.add(comment);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return comments;
    }

    public List<Comment> findAllByMovieId(int movieId) {
        List<Comment> comments = new ArrayList<>();

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_BY_MOVIE_ID_SQL)) {
            ps.setInt(1, movieId);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Comment comment = new Comment();
                comment.setId(resultSet.getInt("id"));
                comment.setAccountId(resultSet.getInt("account_id"));
                comment.setMovieId(resultSet.getInt("movie_id"));
                comment.setNote(resultSet.getString("note"));
                comment.setActive(resultSet.getBoolean("is_active"));
                comment.setCreatedAt(resultSet.getDate("created_at"));
                comment.setUpdatedAt(resultSet.getDate("updated_at"));
                comments.add(comment);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return comments;
    }

    public Comment update(Comment comment) {
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {
            ps.setString(1, comment.getNote());
            ps.setTimestamp(2, new java.sql.Timestamp(comment.getUpdatedAt().getTime()));
            ps.setBoolean(3, comment.getActive());
            ps.setInt(4, comment.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return comment;
    }

    public void deactivateByAccId(int id) {
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(DEACTIVE_COMMENT_BY_ACC_ID_SQL)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
