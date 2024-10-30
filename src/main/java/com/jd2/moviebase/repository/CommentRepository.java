package com.jd2.moviebase.repository;

import com.jd2.moviebase.dto.CommentDto;
import com.jd2.moviebase.exception.MovieDbRepositoryOperationException;
import com.jd2.moviebase.model.Comment;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
            throw new MovieDbRepositoryOperationException("Error while finding all comments", e);
        }

        return comments;
    }

    public Comment findById(Long id) {
        Comment comment = null;
        try (Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(FIND_BY_ID_SQL)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    comment = mapRow(rs);
                }
            }
        } catch (SQLException e) {
            throw new MovieDbRepositoryOperationException("Error while fetching comment by ID " + id, e);
        }

        return Optional.ofNullable(comment)
            .orElseThrow(() -> new MovieDbRepositoryOperationException("Comment with ID " + id + " not found"));
    }

    public Comment create(Comment comment) {
        long insertedId = 0L;
        Date createdAt = null;
        Date updatedAt = null;
        try (Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(CREATE_SQL, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, comment.getAccountId());
            ps.setLong(2, comment.getMovieId());
            ps.setString(3, comment.getNote());
            ps.setBoolean(4, comment.getIsActive());
            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    insertedId = generatedKeys.getLong(1);
                    createdAt = generatedKeys.getDate(5);
                    updatedAt = generatedKeys.getDate(6);
                    comment.setId(insertedId);
                    comment.setCreatedAt(createdAt.toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime());
                    comment.setUpdatedAt(updatedAt.toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime());
                } else {
                    throw new SQLException("Creating comment failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            throw new MovieDbRepositoryOperationException("Error while creating comment", e);
        }
        return comment;
    }

    public Comment update(Comment comment) {
        Date createdAt = null;
        Date updatedAt = null;
        try (Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {
            ps.setString(1, comment.getNote());
            ps.setDate(2, new java.sql.Date(System.currentTimeMillis()));
            ps.setBoolean(3, comment.getIsActive());
            ps.setLong(4, comment.getId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Updating comment failed, no rows affected.");
            }
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                createdAt = generatedKeys.getDate(10);
                updatedAt = generatedKeys.getDate(11);
                comment.setCreatedAt(createdAt.toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime());
                comment.setUpdatedAt(updatedAt.toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime());
            }
        } catch (SQLException e) {
            throw new MovieDbRepositoryOperationException("Error updating comment", e);
        }
        return comment;
    }

    public void deactivateByAccId(Long id) {
        try (Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(DEACTIVATE_COMMENT_BY_ACC_ID_SQL)) {

            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new MovieDbRepositoryOperationException("Error deactivating comment", e);
        }
    }

    private Comment mapRow(ResultSet rs) throws SQLException {
        return Comment.builder()
            .id(rs.getLong("id"))
            .accountId(rs.getLong("account_id"))
            .movieId(rs.getLong("movie_id"))
            .note(rs.getString("note"))
            .createdAt(rs.getTimestamp("created_at").toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime())
            .updatedAt(rs.getTimestamp("updated_at").toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime())
            .isActive(rs.getBoolean("is_active"))
            .build();
    }
}
