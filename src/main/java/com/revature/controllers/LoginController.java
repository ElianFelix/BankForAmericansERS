package com.revature.controllers;

import java.util.List;
import java.util.Map;

import org.eclipse.jetty.util.security.Credential.MD5;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.revature.models.User;
import com.revature.services.JwtService;
import com.revature.services.UserService;
import com.revature.services.UserServiceImpl;
import com.revature.utils.JWTAuth;

import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;
import io.javalin.validation.BodyValidator;
import io.jsonwebtoken.Claims;

public class LoginController {
	
	private static UserService userService = new UserServiceImpl();
	
	public static Handler userLogin = ctx -> {
		
		Map<String, List<String>> credentials =  ctx.formParamMap();
		String userName = credentials.get("userName").get(0);
		String password = credentials.get("password").get(0);
		
		System.out.println(userName + " " + password);
		if (userName == null || password == null) {
			ctx.sessionAttribute("currentUser", null);
			ctx.json("wrong username or password."); 
			ctx.status(HttpStatus.UNAUTHORIZED);
		} else { 
			User userAuth = userService.getUserByUserName(userName);
			
			System.out.println(MD5.digest("123"));
			if (MD5.digest(password).substring(4).equals(userAuth.getPasswordHash())) {
				String jwt = JwtService.createJWToken(userAuth);
				System.out.println(jwt);
				ctx.json(jwt);
			} else {
				ctx.json("wrong username or password."); 
				ctx.status(HttpStatus.UNAUTHORIZED);
			}

		}
	};
	
	public static Handler userLogOut = ctx -> {
		
	};
	
	public static Handler authorize = ctx -> {
		Claims  userClaims;
		
		if (ctx.header("Authorization") != null) {
			userClaims = JWTAuth.verifyAuthJWT(ctx.header("Authorization"));
		
			if (userClaims != null) {
				ctx.attribute("claims", userClaims); 
				return;
			}
		}
		
		ctx.json("Need a valid jwt to access this resource."); 
		ctx.status(HttpStatus.UNAUTHORIZED);
		
		
	};
}