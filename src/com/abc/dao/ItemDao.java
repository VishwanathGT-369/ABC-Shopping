package com.abc.dao;

import com.abc.db.MysqlConnection;
import com.abc.models.Item;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ItemDao {

    private Connection con;

    public ItemDao() {
        con = MysqlConnection.getConnection();
    }

    public List<Item> getItems() {
        List<Item> itemsList = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            String sqlQuery = "select * from eshoppingdb.ITEM";
            ResultSet rs = stmt.executeQuery(sqlQuery);
            OrderDao.setItems(rs, itemsList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itemsList;
    }
}
