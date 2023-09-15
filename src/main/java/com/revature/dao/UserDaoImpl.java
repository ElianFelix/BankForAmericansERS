package com.revature.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.models.User;
import com.revature.utils.DbConn;

public class UserDaoImpl implements UserDao {
	
	public static Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
	
	private Connection conn;

	public UserDaoImpl() {
		conn = DbConn.getDbConnection();
	}
	
	@Override
	public List<User> getUsers() {
		List<User> userList = new ArrayList<>();
		String query = String.format("SELECT * FROM \"User\";");
		logger.info("Our query to retrieve all users: \n" + query);
		
		try (Statement stmt = conn.createStatement()) {
			ResultSet rSet = stmt.executeQuery(query);
			
			
			while (rSet.next()) {
				User rUser = new User(rSet.getInt("UserId"),
										rSet.getString("UserName"),
										rSet.getString("PasswordHash"),
										rSet.getInt("RoleId"),
										rSet.getString("FirstName"),
										rSet.getString("LastName"),
										rSet.getString("Email"));
										
				userList.add(rUser);
			}
			
			return userList;
		} catch (SQLException e) {
			System.out.println(e.getStackTrace());
		}
		
		return null;
	}
	
	@Override
	public int create(User user) {
		String query = String.format("INSERT INTO \"User\" ( \"RoleId\", \"PasswordHash\", \"LastName\", \"Email\", \"FirstName\", \"UserName\")\n"
				+ "            VALUES (1, '%s', '%s', '%s', '%s', '%s') RETURNING \"UserId\";", 
				user.getPasswordHash(),
				user.getLastName(),
				user.getEmail(),
				user.getFirstName(),
				user.getUserName());
		logger.info("Our query to create user: \n" + query);
		
		try (Statement stmt = conn.createStatement()) {
			ResultSet rSet = stmt.executeQuery(query);
			while (rSet.next())
				return rSet.getInt(1);
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
		
		return 0;
	}

	@Override
	public User getUserById(int userId) {
		User user = new User();
		String query = String.format("SELECT * FROM \"User\" WHERE \"UserId\" = '%d';", userId);
		logger.info("Our query to retrieve user: \n" + query);
		
		try (Statement stmt = conn.createStatement()) {
			ResultSet rSet = stmt.executeQuery(query);
			
			while (rSet.next()) {
				user.setUserId(rSet.getInt("UserId"));
				user.setUserName(rSet.getString("UserName"));
				user.setFirstName(rSet.getString("LastName"));
				user.setLastName(rSet.getString("FirstName"));
				user.setPasswordHash(rSet.getString("PasswordHash"));
				user.setEmail(rSet.getString("Email"));
				user.setUserRoleId(rSet.getInt("RoleId"));
			}
			
			return user;
		} catch (SQLException e) {
			System.out.println(e.getStackTrace());
		}
		
		return null;
	}
	
	@Override
	public User getUserByUserName(String userName) {
		User user = new User();
		String query = String.format("SELECT * FROM \"User\" WHERE \"UserName\" = '%s';", userName);
		logger.info("Our query to retrieve user: \n" + query);
		
		try (Statement stmt = conn.createStatement()) {
			ResultSet rSet = stmt.executeQuery(query);
			
			while (rSet.next()) {
				user.setUserId(rSet.getInt("UserId"));
				user.setUserName(rSet.getString("UserName"));
				user.setFirstName(rSet.getString("LastName"));
				user.setLastName(rSet.getString("FirstName"));
				user.setPasswordHash(rSet.getString("PasswordHash"));
				user.setEmail(rSet.getString("Email"));
				user.setUserRoleId(rSet.getInt("RoleId"));
			}
			
			return user;
		} catch (SQLException e) {
			System.out.println(e.getStackTrace());
		}
		
		return null;
	}

	@Override
	public boolean updateUser(User user) {
		int updatedId = 0;
		String query = String.format("UPDATE \"User\" SET (\"PasswordHash\", \"LastName\", \"Email\", \"FirstName\", \"UserName\")\n"
				+ " = ('%s', '%s', '%s', '%s', '%s') WHERE \"UserId\" = %d RETURNING \"UserId\";", 
				user.getPasswordHash(),
				user.getLastName(),
				user.getEmail(),
				user.getFirstName(),
				user.getUserName(),
				user.getUserId());
		logger.info("Our query to update user: \n" + query);
		
		try (Statement stmt = conn.createStatement()) {
			ResultSet rSet = stmt.executeQuery(query);
			while (rSet.next())
				updatedId = rSet.getInt(1);
			return updatedId == user.getUserId();
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
		
		return false;
	}
	
	@Override
	public boolean updateUserRole(User user) {
		int updatedId = 0;
		String query = String.format("UPDATE \"User\" SET \"RoleId\" = %d WHERE \"UserId\" = %d RETURNING \"UserId\";", 
				user.getUserRoleId(),
				user.getUserId());
		logger.info("Our query to update user: \n" + query);
		
		try (Statement stmt = conn.createStatement()) {
			ResultSet rSet = stmt.executeQuery(query);
			while (rSet.next())
				updatedId = rSet.getInt(1);
			return updatedId == user.getUserId();
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
		
		return false;
	}

	@Override
	public boolean deleteUserById(int userId) {
		int deletedId = 0;
		String query = String.format("DELETE FROM \"User\" WHERE \"UserId\" = %d RETURNING \"UserId\";", userId);
		logger.info("Our query to delete user: \n" + query);
		
		try (Statement stmt = conn.createStatement()) {
			ResultSet rSet = stmt.executeQuery(query);
			
			
			while (rSet.next()) {
				deletedId = rSet.getInt(1);
			}
			
			return deletedId == userId;
		} catch (SQLException e) {
			System.out.println(e.getStackTrace());
		}
		
		return false;
	}

}
