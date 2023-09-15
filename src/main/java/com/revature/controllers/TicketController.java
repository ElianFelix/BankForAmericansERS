package com.revature.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.revature.models.ReimbTicket;
import com.revature.models.User;
import com.revature.services.JwtService;
import com.revature.services.ReimbTicketService;
import com.revature.services.ReimbTicketServiceImpl;
import com.revature.services.UserService;
import com.revature.services.UserServiceImpl;
import com.revature.utils.JWTAuth;

import ch.qos.logback.core.net.SyslogOutputStream;
import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;
import io.javalin.validation.BodyValidator;
import io.jsonwebtoken.Claims;

public class TicketController {

	private static ReimbTicketService ticketService = new ReimbTicketServiceImpl();
	
	public static Handler getTickets = ctx -> {
		if (ctx.status() ==  HttpStatus.UNAUTHORIZED) {
			return;
		}
		
		Claims userClaims = ctx.attribute("claims");
		
		if (!userClaims.get("authLevel", Integer.class).equals(2)) {
			ctx.json("You have no access to this resource.");
			ctx.status(HttpStatus.FORBIDDEN);
			return;
		}
		
		List<ReimbTicket> tickets = ticketService.getTickets();
		
		if (!tickets.isEmpty()) {
			tickets.forEach(u -> System.out.println(u.toString()));
			ctx.json(tickets);
		} else {
			ctx.json("No tickets exists at this time.");
			ctx.status(HttpStatus.NOT_FOUND);
		}
	};
	
	public static Handler createTicket = ctx -> {
		if (ctx.status() ==  HttpStatus.UNAUTHORIZED) {
			return;
		}
		
		Claims userClaims = ctx.attribute("claims");
		
		ReimbTicket ticketInput = ctx.bodyAsClass(ReimbTicket.class);
		ticketInput.setStartedBy(userClaims.get("subjectId", Integer.class));
		
		/* Awesome input validation method *TAKE NOTE*
		 * User userInput = ctx.bodyValidator(User.class).check(obj ->
		 * obj.getUserRoleId() == 1, "THINGS_MUST_BE_EQUAL") .get();
		 */
		
		System.out.println(ticketInput.toString());
		
		int ticketId = ticketService.create(ticketInput);
		  
		if (ticketId != 0) {
			ctx.json(ticketId);
			ctx.status(HttpStatus.CREATED); 
		} else {
			ctx.json("Could not create ticket.");
			ctx.status(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	};
	
	public static Handler getUserTicketsByType = ctx -> {
		if (ctx.status() ==  HttpStatus.UNAUTHORIZED) {
			return;
		}
		
		String filterVal = ctx.pathParam("typeId");
		String userId = ctx.pathParam("userId");
		
		Claims userClaims = ctx.attribute("claims");
		
		if (!userClaims.get("authLevel", Integer.class).equals(2) && !userClaims.get("subjectId", Integer.class).equals(Integer.parseInt(userId))) {
			ctx.json("You have no access to this resource.");
			ctx.status(HttpStatus.FORBIDDEN);
			return;
		}
		
		List<ReimbTicket> tickets = ticketService.getFilteredTickets("TypeId", filterVal, userId);
		
		if (!tickets.isEmpty()) {
			tickets.forEach(u -> System.out.println(u.toString()));
			ctx.json(tickets);
		} else {
			ctx.json("No tickets exists at this time.");
			ctx.status(HttpStatus.NOT_FOUND);
		}
	};
	
	public static Handler getUserTicketsByStatus = ctx -> {
		if (ctx.status() ==  HttpStatus.UNAUTHORIZED) {
			return;
		}
		
		String filterVal = ctx.pathParam("statusId");
		String userId = ctx.pathParam("userId");
		
		Claims userClaims = ctx.attribute("claims");
		
		if (!userClaims.get("authLevel", Integer.class).equals(2) && !userClaims.get("subjectId", Integer.class).equals(Integer.parseInt(userId))) {
			ctx.json("You have no access to this resource.");
			ctx.status(HttpStatus.FORBIDDEN);
			return;
		}
		
		List<ReimbTicket> tickets = ticketService.getFilteredTickets("StatusId", filterVal, userId);
		
		if (!tickets.isEmpty()) {
			tickets.forEach(u -> System.out.println(u.toString()));
			ctx.json(tickets);
		} else {
			ctx.json("No tickets exists at this time.");
			ctx.status(HttpStatus.NOT_FOUND);
		}
	};
	
	public static Handler getUserTickets = ctx -> {
		if (ctx.status() ==  HttpStatus.UNAUTHORIZED) {
			return;
		}
		
		String userId = ctx.pathParam("userId");
		
		Claims userClaims = ctx.attribute("claims");
		
		if (!userClaims.get("authLevel", Integer.class).equals(2) && !userClaims.get("subjectId", Integer.class).equals(Integer.parseInt(userId))) {
			ctx.json("You have no access to this resource.");
			ctx.status(HttpStatus.FORBIDDEN);
			return;
		}
		
		List<ReimbTicket> tickets = ticketService.getFilteredTickets(null, null, userId);
		
		if (!tickets.isEmpty()) {
			tickets.forEach(u -> System.out.println(u.toString()));
			ctx.json(tickets);
		} else {
			ctx.json("No tickets exists at this time.");
			ctx.status(HttpStatus.NOT_FOUND);
		}
	};
	
	public static Handler getTicketById = ctx -> {
		if (ctx.status() ==  HttpStatus.UNAUTHORIZED) {
			return;
		}
		
		int ticketId = Integer.parseInt(ctx.pathParam("ticketId"));
		
		ReimbTicket ticket = ticketService.getTicketById(ticketId);
		
		if (ticket != null && ticket.getReimbId() != 0) {
			ctx.json(ticket);
		} else {
			ctx.json("Error during ticket search by that id. Verify Id and try again.");
			ctx.status(HttpStatus.NOT_FOUND);
		}
	};
	
	public static Handler removeTicket = ctx -> {
		if (ctx.status() ==  HttpStatus.UNAUTHORIZED) {
			return;
		}
		
		Claims userClaims = ctx.attribute("claims");
		
		if (!userClaims.get("authLevel", Integer.class).equals(2)) {
			ctx.json("You have no access to this resource.");
			ctx.status(HttpStatus.FORBIDDEN);
			return;
		}
		
		int ticketId = Integer.parseInt(ctx.pathParam("ticketId"));
		
		boolean deleted = ticketService.deleteTicket(ticketId);
		
		if (deleted != false) {
			ctx.json(deleted);
		} else {
			ctx.json("Error during ticket search by that id. Verify Id and try again.");
			ctx.status(HttpStatus.NOT_FOUND);
		}
	};
	
	public static Handler updateTicket = ctx -> {
		if (ctx.status() ==  HttpStatus.UNAUTHORIZED) {
			return;
		}
		
		Claims userClaims = ctx.attribute("claims");
		
		if (!userClaims.get("authLevel", Integer.class).equals(2)) {
			ctx.json("You have no access to this resource.");
			ctx.status(HttpStatus.FORBIDDEN);
			return;
		}
		
		ReimbTicket ticketInput = ctx.bodyAsClass(ReimbTicket.class);
		ticketInput.setResolver(userClaims.get("subjectId", Integer.class));
		
		/* Awesome input validation method *TAKE NOTE*
		 * User userInput = ctx.bodyValidator(User.class).check(obj ->
		 * obj.getUserRoleId() == 1, "THINGS_MUST_BE_EQUAL") .get();
		 */
		
		System.out.println(ticketInput.toString());
		
		boolean updated = ticketService.updateTicket(ticketInput);
		  
		if (updated != false) {
			ctx.json(updated);
			ctx.status(HttpStatus.OK); 
		} else {
			ctx.json("Could not update ticket.");
			ctx.status(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	};
	
}
