package com.jd2.moviebase.repository;

import com.jd2.moviebase.config.DataSource;
import com.jd2.moviebase.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CommentRepository {

    private static final String CREATE_SQL = "INSERT INTO comments (account_id, movie_id, note, is_active) VALUES (?, ?, ?, ?)";
    private static final String FIND_BY_ID_SQL = "SELECT * FROM comments WHERE id = ?";
    private static final String FIND_SQL = "SELECT * FROM comments";
    private static final String UPDATE_SQL = "UPDATE comments SET account_id = ?, movie_id = ?, note = ?, updated_at = ?, is_active = ? WHERE id = ?";
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

    public Optional<Comment> findById(int id) {
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

    public Comment create(Comment comment) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(CREATE_SQL, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, comment.getAccountId());
            ps.setInt(2, comment.getMovieId());
            ps.setString(3, comment.getNote());
            ps.setBoolean(4, comment.getActive());
            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    comment.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating comment failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return comment;
    }

    public Comment update(Comment comment) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {

            ps.setInt(1, comment.getAccountId());
            ps.setInt(2, comment.getMovieId());
            ps.setString(3, comment.getNote());
            ps.setTimestamp(4, new Timestamp(comment.getUpdatedAt().getTime()));
            ps.setBoolean(5, comment.getActive());
            ps.setInt(6, comment.getId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Updating comment failed, no rows affected.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return comment;
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
        Comment comment = new Comment();
        comment.setId(rs.getInt("id"));
        comment.setAccountId(rs.getInt("account_id"));
        comment.setMovieId(rs.getInt("movie_id"));
        comment.setNote(rs.getString("note"));
        comment.setCreatedAt(rs.getDate("created_at"));
        comment.setUpdatedAt(rs.getDate("updated_at"));
        comment.setActive(rs.getBoolean("is_active"));
        return comment;
    }
}
