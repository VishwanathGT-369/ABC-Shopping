package com.abc.services;

import com.abc.models.User;

import java.io.IOException;
import java.util.Optional;

public interface UserService {
	public  Optional<User>  checkValidation(String username, String password);
	public Optional<User> getUserInputs() throws IOException;
}
