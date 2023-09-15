package com.revature.controllers;

import java.io.InputStream;

import com.revature.models.Picture;
import com.revature.models.ReimbTicket;
import com.revature.services.PictureService;
import com.revature.services.ReimbTicketService;
import com.revature.services.ReimbTicketServiceImpl;

import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;
import io.jsonwebtoken.Claims;

public class PictureController {
	
	private static PictureService pictureService = new PictureService();
	
	public static Handler createProfPicture = ctx -> {
		if (ctx.status() ==  HttpStatus.UNAUTHORIZED) {
			return;
		}
		
		Claims userClaims = ctx.attribute("claims");
		
		Picture pic = new Picture();
		pic.resourceId = (userClaims.get("subjectId", Integer.class));
		pic.data = ctx.uploadedFile("picture");
		
		
		System.out.println(pic.toString());
		
		
		boolean created = pictureService.createPicture(pic, "pictures/profiles/profile");
		  
		if (created) {
			ctx.json(created);
			ctx.status(HttpStatus.CREATED); 
		} else {
			ctx.json("Could not create picture.");
			ctx.status(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	};
	
	public static Handler getProfPicture = ctx -> {
		if (ctx.status() ==  HttpStatus.UNAUTHORIZED) {
			return;
		}
		
		Claims userClaims = ctx.attribute("claims");
		
		int resourceId = !ctx.pathParam("userId").equals(":userId") ? Integer.parseInt(ctx.pathParam("userId")) 
				: (userClaims.get("subjectId", Integer.class));
		
		InputStream pic = pictureService.getPicture(resourceId, "pictures/profiles/profile");
		  
		if (pic != null) {
			ctx.header("content-type", "image/jpeg");
			ctx.result(pic);
			ctx.status(HttpStatus.OK);
		} else {
			ctx.json("Could not find picture.");
			ctx.status(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	};
	
	public static Handler createReceiptPicture = ctx -> {
		if (ctx.status() ==  HttpStatus.UNAUTHORIZED) {
			return;
		}
		
		Claims userClaims = ctx.attribute("claims");
		
		Picture pic = new Picture();
		pic.resourceId = Integer.parseInt(ctx.pathParam("ticketId"));
		pic.data = ctx.uploadedFile("picture");
		
		
		System.out.println(pic.toString());
		
		boolean created = pictureService.createPicture(pic, "pictures/receipts/receipt");
		  
		if (created) {
			ctx.json(created);
			ctx.status(HttpStatus.CREATED); 
		} else {
			ctx.json("Could not create picture.");
			ctx.status(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	};
	
	public static Handler getReceiptPicture = ctx -> {
		if (ctx.status() ==  HttpStatus.UNAUTHORIZED) {
			return;
		}
		
		Claims userClaims = ctx.attribute("claims");
		
		int resourceId = Integer.parseInt(ctx.pathParam("ticketId"));
		
		InputStream pic = pictureService.getPicture(resourceId, "pictures/receipts/receipt");
		  
		if (pic != null) {
			ctx.header("content-type", "image/jpeg");
			ctx.result(pic);
			ctx.status(HttpStatus.OK);
		} else {
			ctx.json("Could not find picture.");
			ctx.status(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	};
}
