package com.revature.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.revature.models.User;
import com.revature.services.JwtService;
import com.revature.services.UserService;
import com.revature.services.UserServiceImpl;
import com.revature.utils.JWTAuth;

import ch.qos.logback.core.net.SyslogOutputStream;
import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;
import io.javalin.validation.BodyValidator;
import io.jsonwebtoken.Claims;

public class UserController {
	
	private static UserService userService = new UserServiceImpl();
	
	public static Handler getUsers = ctx -> {
		if (ctx.status() ==  HttpStatus.UNAUTHORIZED) {
			return;
		}
		
		Claims userClaims = ctx.attribute("claims");
		
		if (!userClaims.get("authLevel", Integer.class).equals(2)) {
			ctx.json("You have no access to this resource.");
			ctx.status(HttpStatus.FORBIDDEN);
			return;
		}
		
		List<User> registeredUsers = userService.getUsers();
		
		if (registeredUsers != null && !registeredUsers.isEmpty()) {
			registeredUsers.forEach(u -> System.out.println(u.toString()));
			ctx.json(registeredUsers);
		} else {
			ctx.json("No registered users exists at this time.");
			ctx.status(HttpStatus.NOT_FOUND);
		}
	};
	
	public static Handler getUserById = ctx -> {
		if (ctx.status() ==  HttpStatus.UNAUTHORIZED) {
			return;
		}
		
		int userId = Integer.parseInt(ctx.pathParam("userId"));
		
		Claims userClaims = ctx.attribute("claims");
		
		if (!userClaims.get("authLevel", Integer.class).equals(2) && !userClaims.get("subjectId", Integer.class).equals(userId)) {
			ctx.json("You have no access to this resource.");
			ctx.status(HttpStatus.FORBIDDEN);
			return;
		}
		
		User user = userService.getUserById(userId);
		
		if (user != null && user.getUserName() != null) {
			ctx.json(user);
		} else {
			ctx.json("Error during user search by that id. Try again.");
			ctx.status(HttpStatus.NOT_FOUND);
		}
	};
	
	public static Handler getUserByUserName = ctx -> {
		if (ctx.status() ==  HttpStatus.UNAUTHORIZED) {
			return;
		}
		
		String userName = ctx.pathParam("userName");
		
		Claims userClaims = ctx.attribute("claims");
		
		if (!userClaims.get("authLevel", Integer.class).equals(2) && !userClaims.get("sub", String.class).equals(userName)) {
			ctx.json("You have no access to this resource.");
			ctx.status(HttpStatus.FORBIDDEN);
			return;
		}
		
		User user = userService.getUserByUserName(userName);
		
		if (user != null && user.getUserName() != null) {
			ctx.json(user);
		} else {
			ctx.json("Error during user search by that id. Try again.");
			ctx.status(HttpStatus.NOT_FOUND);
		}
	};
	
	public static Handler createUser = ctx -> {		
		User userInput = ctx.bodyAsClass(User.class);
		
		/* Awesome input validation method *TAKE NOTE*
		 * User userInput = ctx.bodyValidator(User.class).check(obj ->
		 * obj.getUserRoleId() == 1, "THINGS_MUST_BE_EQUAL") .get();
		 */
		
		System.out.println(userInput.toString());
		
		int userId = userService.createUser(userInput);
		  
		if (userId != 0) {
			ctx.json(userId);
			ctx.status(HttpStatus.CREATED); 
		} else {
			ctx.json("Could not create user. Check that either username or email aren't being used.");
			ctx.status(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	};

	public static Handler updateUser = ctx -> {
		if (ctx.status() ==  HttpStatus.UNAUTHORIZED) {
			return;
		}
		
		User userInput = ctx.bodyAsClass(User.class);
		
		Claims userClaims = ctx.attribute("claims");
		
		if (!userClaims.get("authLevel", Integer.class).equals(2) && !userClaims.get("subjectId", Integer.class).equals(userInput.getUserId())) {
			ctx.json("You have no access to this resource.");
			ctx.status(HttpStatus.FORBIDDEN);
			return;
		}
		
		System.out.println(userInput.toString());
		
		boolean updated = userService.updateUser(userInput);
		  
		if (updated) {
			ctx.json(updated);
		} else {
			ctx.json("Could not update user. Check that either username or email aren't being used.");
			ctx.status(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	};
	
	public static Handler updateUserRole = ctx -> {
		if (ctx.status() ==  HttpStatus.UNAUTHORIZED) {
			return;
		}
		
		User userInput = new User();
		userInput.setUserId(Integer.parseInt(ctx.pathParam("userId")));
		userInput.setUserRoleId(Integer.parseInt(ctx.pathParam("roleId")));
		
		Claims userClaims = ctx.attribute("claims");
		
		if (!userClaims.get("authLevel", Integer.class).equals(2)) {
			ctx.json("You have no access to this resource.");
			ctx.status(HttpStatus.FORBIDDEN);
			return;
		}		
		
		System.out.println(userInput.toString());
		
		boolean updated = userService.updateUserRole(userInput);
		  
		if (updated) {
			ctx.json(updated);
		} else {
			ctx.json("Could not update user. Check that either username or email aren't being used.");
			ctx.status(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	};

	public static Handler removeUser = ctx -> {
		if (ctx.status() ==  HttpStatus.UNAUTHORIZED) {
			return;
		}
		
		int userId = Integer.parseInt(ctx.pathParam("userId"));
		
		Claims userClaims = ctx.attribute("claims");
		
		if (!userClaims.get("authLevel", Integer.class).equals(2)) {
			ctx.json("You have no access to this resource.");
			ctx.status(HttpStatus.FORBIDDEN);
			return;
		}
		
		boolean deleted = userService.deleteUser(userId);
		
		if (deleted) {
			ctx.json(deleted);
		} else {
			ctx.json("This userId doesn't exist");
			ctx.status(HttpStatus.NOT_FOUND);
		}
	};

	
	
}
