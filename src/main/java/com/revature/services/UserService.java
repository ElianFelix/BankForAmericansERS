package com.revature.services;

import java.util.List;

import com.revature.models.User;

public interface UserService {
	List<User> getUsers();
	
	int createUser(User user);
	
	User getUserById(int userId);
	
	User getUserByUserName(String userName);
	
	boolean updateUser(User user);
	
	boolean updateUserRole(User user);
	
	boolean deleteUser(int userId);
}
