package com.abc.dao;

import com.abc.db.MysqlConnection;
import com.abc.models.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class UserDao {

    private Connection con;

    public UserDao() {
        con = MysqlConnection.getConnection();
    }

    public Optional<User> validateUser(String uname, String password) {
        try {

            Statement stmt = con.createStatement();
            String sqlQuery = "select * from eshoppingdb.USER where UNAME = '"+uname+"' and PASSWORD = '"+password+"';";
            ResultSet rs = stmt.executeQuery(sqlQuery);
            if(rs.next()) {
              User user = new User();
              user.setUid(rs.getInt("UID"));
              user.setUserName(rs.getString("USERNAME"));
              return Optional.of(user);
            }
        } catch (SQLException e) {
            System.out.println("Not able to query the DB : "+e.getMessage());
        }
        return Optional.empty();
    }
}
