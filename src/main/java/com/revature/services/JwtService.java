package com.revature.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;

import com.revature.models.User;

public class JwtService {
	
	private static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	
	public static String createJWToken(User user) {

		JwtBuilder jwtB = Jwts.builder();
		
		String jwt = jwtB.setSubject(user.getUserName())
				.claim("authLevel", user.getUserRoleId())
				.claim("subjectId", user.getUserId())
				.signWith(JwtService.key)
				.compact();
		
		return jwt;
	}
	
	public static Claims parseJWToken(String jwt) {
		
		try {
			Jws<Claims> jws = Jwts.parserBuilder()
					.setSigningKey(JwtService.key)
					.build()
					.parseClaimsJws(jwt);
			
			return jws.getBody();
		} catch (JwtException ex) {
			System.out.println(ex.getMessage());
			ex.getStackTrace();
		}
		
		return null;
	}
}


