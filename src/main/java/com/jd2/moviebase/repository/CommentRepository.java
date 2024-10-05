package com.jd2.moviebase.repository;

import com.jd2.moviebase.dto.CommentDto;
import com.jd2.moviebase.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class CommentRepository {

    private static final String CREATE_SQL = "INSERT INTO comments (account_id, movie_id, note, is_active) VALUES (?, ?, ?, ?)";
    private static final String FIND_BY_ID_SQL = "SELECT * FROM comments WHERE id = ?";
    private static final String FIND_SQL = "SELECT * FROM comments";
    private static final String UPDATE_SQL = "UPDATE comments SET note = ?, updated_at = ?, is_active = ? WHERE id = ?";
    private static final String DEACTIVATE_COMMENT_BY_ACC_ID_SQL = "UPDATE comments SET is_active = false, account_id = NULL WHERE account_id = ?";

    private final DataSource dataSource;

    @Autowired
    public CommentRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Comment> findAll() {
        List<Comment> comments = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(FIND_SQL)) {

            while (rs.next()) {
                comments.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return comments;
    }

    public Comment findById(int id) {
        Comment comment = null;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_BY_ID_SQL)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    comment = mapRow(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.ofNullable(comment)
                .orElseThrow(() -> new RuntimeException("Comment with ID " + id + " not found"));
    }

    public Comment create(CommentDto commentDto) {
        int insertedId = 0;
        Date createdAt = null;
        Date updatedAt = null;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(CREATE_SQL, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, commentDto.getAccountId());
            ps.setInt(2, commentDto.getMovieId());
            ps.setString(3, commentDto.getNote());
            ps.setBoolean(4, commentDto.getIsActive());
            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    insertedId = generatedKeys.getInt(1);
                    createdAt = generatedKeys.getDate(5);
                    updatedAt = generatedKeys.getDate(6);
                    commentDto.setId(insertedId);
                } else {
                    throw new SQLException("Creating comment failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new Comment(commentDto.getId(), commentDto.getAccountId(), commentDto.getMovieId(),
                commentDto.getNote(), createdAt, updatedAt, commentDto.getIsActive());
    }

    public Comment update(int id, CommentDto commentDto) {
        Date createdAt = null;
        Date updatedAt = null;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {
            ps.setString(1, commentDto.getNote());
            ps.setDate(2, new java.sql.Date(System.currentTimeMillis()));
            ps.setBoolean(3, commentDto.getIsActive());
            ps.setInt(4, id);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Updating comment failed, no rows affected.");
            }
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                createdAt = generatedKeys.getDate(10);
                updatedAt = generatedKeys.getDate(11);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new Comment(id, commentDto.getAccountId(), commentDto.getMovieId(),
                commentDto.getNote(), createdAt, updatedAt, commentDto.getIsActive());
    }


    public void deactivateByAccId(int id) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(DEACTIVATE_COMMENT_BY_ACC_ID_SQL)) {

            ps.setInt(1, id);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Deactivating comment failed, no rows affected.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Comment mapRow(ResultSet rs) throws SQLException {
        return new Comment(
                rs.getInt("id"),
                rs.getInt("account_id"),
                rs.getInt("movie_id"),
                rs.getString("note"),
                rs.getDate("created_at"),
                rs.getDate("updated_at"),
                rs.getBoolean("is_active")
        );
    }
}
