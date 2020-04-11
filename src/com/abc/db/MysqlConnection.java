package com.abc.db;

import com.abc.application.EShopProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Singleton class for db connection
final public class MysqlConnection {

    private static Connection con;

    private MysqlConnection() {   }

    public static Connection getConnection() {
        if(con == null) {
            synchronized (MysqlConnection.class) {
                if(con == null) {
                    try{
                        Class.forName("com.mysql.jdbc.Driver");
                        String dbName = EShopProperties.getProperty("db_name");
                        String dbPort = EShopProperties.getProperty("db_port");
                        String dbUserName = EShopProperties.getProperty("db_user_name");
                        String dbUserPassword = EShopProperties.getProperty("db_user_password");
                        con= DriverManager.getConnection("jdbc:mysql://localhost:"+dbPort+"/"+dbName, dbUserName, dbUserPassword);
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
        return con;
    }

    public static void close() {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
