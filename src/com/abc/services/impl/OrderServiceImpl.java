package com.abc.services.impl;

import com.abc.dao.OrderDao;
import com.abc.models.Order;
import com.abc.services.OrderService;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl implements OrderService {
	private OrderDao orderDao;

	public OrderServiceImpl() {
		super();
		orderDao = new OrderDao();
	}

	@Override
	public void addItemsToOrder(Order order) {
		orderDao.placeOrder(order);
	}

	@Override
	public List<Order> getOrders(int uid) {
		return orderDao.getOrders(uid);
	}

}
