package com.revature.services;

import com.revature.dao.UserDao;
import com.revature.dao.UserDaoImpl;
import com.revature.models.User;

import java.util.List;

import org.eclipse.jetty.util.security.Credential.MD5;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserServiceImpl implements UserService {
	private UserDao userDao;
	
	public UserServiceImpl() {
		userDao = new UserDaoImpl();
	}
	
	@Override
	public List<User> getUsers() {
		return userDao.getUsers();
	}
	
	@Override
	public int createUser(User user) {
		String plainPass = user.getPasswordHash();
		
		user.setPasswordHash(MD5.digest(plainPass).substring(4));
		
		return userDao.create(user);
	}

	@Override
	public User getUserById(int userId) {
		return userDao.getUserById(userId);
	}
	
	@Override
	public User getUserByUserName(String userName){
		return userDao.getUserByUserName(userName);
	}

	@Override
	public boolean updateUser(User user) {
		if (user.getPasswordHash() != null) {
			String plainPass = user.getPasswordHash();
			user.setPasswordHash(MD5.digest(plainPass).substring(4));
		}
		
		User currentUser = userDao.getUserById(user.getUserId());
		
		if (user.equals(currentUser)) {
			return false;
		} else {
			if (user.getUserName() == null) user.setUserName(currentUser.getUserName());
			if (user.getPasswordHash() == null) user.setPasswordHash(currentUser.getPasswordHash());
			if (user.getFirstName() == null) user.setFirstName(currentUser.getFirstName());
			if (user.getLastName() == null) user.setLastName(currentUser.getLastName());
			if (user.getEmail() == null) user.setEmail(currentUser.getEmail());
		}
		
		return userDao.updateUser(user);
	}
	
	@Override
	public boolean updateUserRole(User user) {
		return userDao.updateUserRole(user);
	}

	@Override
	public boolean deleteUser(int userId) {
		return userDao.deleteUserById(userId);
	}


}
