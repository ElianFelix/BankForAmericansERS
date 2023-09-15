package com.revature.dao;

import java.util.List;

import com.revature.models.User;

public interface UserDao {

	List<User> getUsers();
	
	int create(User user);
	
	User getUserById(int userId);
	
	User getUserByUserName(String userName);
	
	boolean updateUser(User user);
	
	boolean updateUserRole(User user);
	
	boolean deleteUserById(int userId);
	
}
