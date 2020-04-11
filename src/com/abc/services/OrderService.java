package com.abc.services;

import com.abc.models.Order;

import java.util.List;

public interface OrderService {

public List<Order> getOrders(int uid);
void addItemsToOrder(Order order);
}
