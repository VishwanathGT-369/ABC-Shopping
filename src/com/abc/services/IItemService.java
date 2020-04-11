package com.abc.services;

import com.abc.models.Item;

import java.util.List;

public interface IItemService {
	public List<Item> getItems();

	public List<Item> getItemById(String[] input);
}
