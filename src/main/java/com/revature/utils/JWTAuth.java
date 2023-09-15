package com.revature.utils;

import com.revature.services.JwtService;

import io.jsonwebtoken.Claims;

public class JWTAuth {
	
	public static boolean verifyAuthJWT(String jwToken, String reqClaim, String reqLevel) {
		
		Claims userClaims = JwtService.parseJWToken(jwToken.substring(7));
		
		String claimLevel = userClaims.get(reqClaim, String.class);
		
		if (reqLevel.equals(claimLevel)) {
			return true;
		}

		return false;
	}
	
	public static Claims verifyAuthJWT(String jwToken) {
		
		Claims userClaims = JwtService.parseJWToken(jwToken.substring(7));
		
		if (userClaims == null) {
			return null;
		}

		return userClaims;
	}
	
}
