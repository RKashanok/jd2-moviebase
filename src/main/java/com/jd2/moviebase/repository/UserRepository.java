package com.jd2.moviebase.repository;

import com.jd2.moviebase.config.DataSource;
import com.jd2.moviebase.model.User;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class UserRepository {

    private final DataSource ds;

    private final String ADD_USER_SQL = "insert into users (name) values (?)";
    private final String GET_USERS_SQL = "insert into users (name) values (?)";
    private final String GET_USER_SQL = "insert into users (name) values (?)";
    private final String UPDATE_USER_SQL = "insert into users (name) values (?)";
    private final String DELETE_USER_SQL = "insert into users (name) values (?)";

    public UserRepository(DataSource ds) {
        this.ds = ds;
    }

    public User findById(Long id) {
        return null;
    }

    public void deleteById(Long id) {
        try(Connection conn = ds.getConnection())  {
            PreparedStatement ps = conn.prepareStatement("delete from users where id = ?");
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
