package com.abc.dao;

import com.abc.db.MysqlConnection;
import com.abc.models.Item;
import com.abc.models.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OrderDao {
    private Connection con;
    private Lock lock;

    public OrderDao() {
        con = MysqlConnection.getConnection();
        lock = new ReentrantLock();
    }

    public List<Order> getOrders(int uid) {
        List<Order> ordersList = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            String sqlQuery = "select * from eshoppingdb.ORDERS where UID = "+uid+"";
            ResultSet rs = stmt.executeQuery(sqlQuery);
            while(rs.next()) {
                int order_id = rs.getInt("ORDER_ID");
                Order order = new Order();
                order.setOrderId(order_id);
                order.setUserId(uid);
                order.setItemCount(rs.getInt("ITEM_COUNT"));
                order.setTotalBill(rs.getDouble("TOTAL_BILL"));

                Statement stmtItems = con.createStatement();
                sqlQuery = "select I.ITEM_ID, I.NAME, I.DESCRIPTION, I.PRICE from eshoppingdb.ORDER_ITEMS OI join eshoppingdb.ITEM I where OI.ITEM_ID = I.ITEM_ID and OI.ORDER_ID = "+order_id+"";
                ResultSet rsItems = stmtItems.executeQuery(sqlQuery);
                List<Item> itemsList = new ArrayList<>();
                setItems(rsItems, itemsList);

                order.setItemsList(itemsList);
                ordersList.add(order);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ordersList;
    }

    static void setItems(ResultSet rsItems, List<Item> itemsList) throws SQLException {
        while (rsItems.next()) {
            Item item = new Item();
            item.setItemId(rsItems.getInt("ITEM_ID"));
            item.setName(rsItems.getString("NAME"));
            item.setDescription(rsItems.getString("DESCRIPTION"));
            item.setPrice(rsItems.getDouble("PRICE"));
            itemsList.add(item);
        }
    }

    public void placeOrder(Order order) {
        lock.lock();
        try {
            String sqlQuery = "insert into eshoppingdb.ORDERS (UID, ITEM_COUNT, date, TOTAL_BILL) values (?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(sqlQuery);
            pstmt.setInt(1, order.getUserId());
            pstmt.setInt(2, order.getItemCount());
            pstmt.setDate(3, new java.sql.Date(order.getOrderedDate().getTime()));
            pstmt.setDouble(4, order.getTotalBill());
            pstmt.execute();
            sqlQuery = "select max(ORDER_ID) ORD_ID from eshoppingdb.ORDERS";
            ResultSet rs = pstmt.executeQuery(sqlQuery);
            int orderId = 0;
            if(rs.next()) {
                orderId = rs.getInt(1);
            }
            List<Item> itemsList = order.getItemsList();
            for(Item item : itemsList) {
                int itemId = item.getItemId();
                sqlQuery = "insert into eshoppingdb.ORDER_ITEMS (ITEM_ID, ORDER_ID) values ("+itemId+", "+orderId+")";
                pstmt.execute(sqlQuery);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }


    }


}
