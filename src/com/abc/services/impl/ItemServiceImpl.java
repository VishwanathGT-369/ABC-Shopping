package com.abc.services.impl;

import com.abc.dao.ItemDao;
import com.abc.models.Item;
import com.abc.services.IItemService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ItemServiceImpl implements IItemService {
	private List<Item> itemList;
	private ItemDao itemDao;

	
	public ItemServiceImpl() {
		itemDao = new ItemDao();
		itemList = itemDao.getItems();
	}
	
	
	@Override
	public List<Item> getItems() {
		return itemList;
	}

	@Override
	public List<Item> getItemById(String[] input) {
		List<String> asList = Arrays.asList(input);
		List<Item> selectedItemsList = new ArrayList<>();
		for(String inputItemId : input) {
			Optional<Item> itemOptional = itemList.stream().filter(item -> item.getItemId() == Integer.parseInt(inputItemId)).findAny();
			if(!itemOptional.isPresent()) {
				System.out.println("Invalid Item ID : "+inputItemId);
			} else {
				selectedItemsList.add(itemOptional.get());
			}
		}
		if(selectedItemsList.isEmpty()) {
			throw new IllegalArgumentException("Invalid options selected");
		}
		return selectedItemsList;
	}

}
