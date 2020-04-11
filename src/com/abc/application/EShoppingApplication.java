package com.abc.application;

import com.abc.db.MysqlConnection;
import com.abc.services.IItemService;
import com.abc.models.Item;
import com.abc.services.impl.ItemServiceImpl;
import com.abc.models.Order;
import com.abc.services.OrderService;
import com.abc.services.impl.OrderServiceImpl;
import com.abc.services.PrintService;
import com.abc.models.User;
import com.abc.services.UserService;
import com.abc.services.impl.UserServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class EShoppingApplication {

	UserService userService;
	IItemService itemService;
	OrderService orderService;
	PrintService printService;
	
	public EShoppingApplication() {
		userService = new UserServiceImpl();
		itemService = new ItemServiceImpl();
		orderService = new OrderServiceImpl();
	}
	
	public void onStart() {
		System.out.println("***********************************************************************************************");
		System.out.println("*                                                                                             *");
		System.out.println("*                			Welcome to ABC E-Shopping Application 							  *");
		System.out.println("*                                                                                             *");
		System.out.println("***********************************************************************************************");
	}

	public void launchApplication() throws IOException {
		UserService userService = new UserServiceImpl();
		IItemService itemService = new ItemServiceImpl();
		OrderService orderService = new OrderServiceImpl();
		List<Item> userItems = new ArrayList<Item>();
		Set<Integer> itemIds = new HashSet<Integer>();

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		boolean checkValFlag = false;
		int userOpt = 0;
		int itemOpt = -1;

		while (userOpt == 0) {
			Optional<User> userOptional = userService.getUserInputs();

			if (userOptional.isPresent()) {
				User currentUser = userOptional.get();
				while (itemOpt != 0) {
					System.out
							.println("Press 0:LogOut,Press 1:View Items,Press 2:Add item to Order,Press 3:View Orders");
					itemOpt = Integer.parseInt(reader.readLine());

					switch (itemOpt) {
					case 0:
						itemOpt = 0;
						break;

					case 1:
						List<Item> items = itemService.getItems();
						List<String> headers = Arrays.asList("Item ID","Name","Description","Price");
						printService = new PrintService();
						printService.printValuesItems(headers, items);
						break;

					case 2:
						BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

						String[] input;

						System.out.print("Please enter item id to make an order(seperated by a single space): ");
						input = in.readLine().split(" ");
						try {
							userItems = itemService.getItemById(input);
							Order order = new Order();
							order.setItemsList(userItems);
							double total = 0;
							for (Item item : userItems) {
								total += item.getPrice();
							}
							order.setTotalBill(total);
							order.setUserId(currentUser.getUid());
							orderService.addItemsToOrder(order);
							System.out.println("Item quantity \""+order.getItemsList().size()+" placed into the order successfully");
						} catch (IllegalArgumentException e) {
							System.out.println("Item ID's with Space only allowed : "+e.getMessage());
						}
//						int itemId = Integer.parseInt(reader.readLine());
//
						break;

					case 3:
						List<Order> viewOrder = orderService.getOrders(currentUser.getUid());
						List<String> orderHeaders = Arrays.asList("Order ID", "Amount", "Date", "Item Names");
						printService = new PrintService();
						printService.printValuesOrders(orderHeaders, viewOrder);
						break;
					default:
						break;
					}
					// userOpt=3;
				}
			}

			System.out.println("Press 0:Login ,Press 1:Exit");

			userOpt = Integer.parseInt(reader.readLine());

		}
	}

	public void beforeDestroy() {
		MysqlConnection.close();
	}

}
