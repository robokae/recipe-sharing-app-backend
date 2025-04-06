package com.recipedb.api.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class DatabaseUtil {

    @Autowired
    private DataSource dataSource;

    private PreparedStatement getPreparedStatement(Connection con, String query, List<Object> params)
            throws SQLException {

        PreparedStatement ps = con.prepareStatement(query);
        for (int i = 0; i < params.size(); i++) {
            ps.setObject(i + 1, params.get(i));
        }
        return ps;
    }

    public ResultSet executeQuery(String query, List<Object> params) throws SQLException {
        Connection con = dataSource.getConnection();
        PreparedStatement ps = getPreparedStatement(con, query, params);
        return ps.executeQuery();
    }

    public void executeUpdate(String query, List<Object> params) throws SQLException {
        Connection con = dataSource.getConnection();
        PreparedStatement ps = getPreparedStatement(con, query, params);
        ps.executeUpdate();
    }
}
