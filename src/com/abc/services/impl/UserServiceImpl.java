package com.abc.services.impl;

import com.abc.dao.UserDao;
import com.abc.models.User;
import com.abc.services.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

public class UserServiceImpl implements UserService {

	private UserDao userDao;
	public UserServiceImpl() {
		userDao = new UserDao();
	}

	@Override
	public Optional<User> checkValidation(String username, String password) {
		Optional<User> userOptional = userDao.validateUser(username, password);
		if(userOptional.isPresent()) {
			System.out.println("You are Successfully logged on");
			System.out.println("Welcome "+userOptional.get().getUserName());
			return userOptional;
		}else {
			System.out.println("Sorry you have entered wrong credentials");
			return Optional.empty();
		}
		
	}

	@Override
	public Optional<User> getUserInputs() throws IOException {
		User user =new User();
		String username = null;
		String password = null;
		
		boolean validation=false;
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Enter your name: ");
			username = reader.readLine();
			
			System.out.println("Enter your Password: ");
			password = reader.readLine();
			
			Optional<User> userOptional = checkValidation(username.trim(), password.trim());
			
			return userOptional;
	}

}
