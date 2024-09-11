package com.jd2.moviebase.repository;

import com.jd2.moviebase.config.DataSource;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class CommentsRepository {
    private final DataSource ds;

    public CommentsRepository(DataSource ds) {
        this.ds = ds;
    }

    private final String DEACTIVE_COMMENT_BY_ACC_ID_SQL = "UPDATE comments SET is_active = false, account_id = NULL  WHERE account_id = ?";

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
