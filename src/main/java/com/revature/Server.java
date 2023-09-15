package com.revature;

import static io.javalin.apibuilder.ApiBuilder.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.controllers.LoginController;
import com.revature.controllers.PictureController;
import com.revature.controllers.TicketController;
import com.revature.controllers.UserController;

import io.javalin.Javalin;

import io.javalin.http.HttpStatus;

public class Server {

	public static Logger logger = LoggerFactory.getLogger(Server.class);
	
	public static void main(String[] args) {
		Javalin app = Javalin.create().start(8081);
		
		app.before(ctx -> {
			logger.info("Request at URL " + ctx.url() + " has started.");
		});
		
		// routes directory
		app.routes(() -> {
			// root endpoint (plan to send project readme as file with project details
			get("/", ctx -> { 
				System.out.println("Got a request for the root of the api");
				ctx.html("Work in progress...");
				ctx.json("this is a test");
				ctx.status(HttpStatus.NOT_IMPLEMENTED);
			});
			
			// login endpoints:
			post("/login", LoginController.userLogin);
			post("/logout", LoginController.userLogOut); //TODO: This is not possible; only doable way is to set a timeout for the tokens when issued. That or make the user agent delete their token
														// implement the logout method - basically invalidate the provided jwt token. prolly not necessary if token expiration is setup
			// users endpoints:
			before("/users/*", LoginController.authorize);
			path("/users", () -> {
				get(UserController.getUsers);
				post(UserController.createUser);
				path("/{userId}", () -> {
					get( UserController.getUserById);
					delete( UserController.removeUser);
					put("/update-profile", UserController.updateUser);
					put("/update-role/{roleId}", UserController.updateUserRole);
				});
				get("/username/{userName}", UserController.getUserByUserName);
			});

			// tickets endpoints:
			before("/tickets/*", LoginController.authorize);
			path("/tickets", () -> {
				get(TicketController.getTickets);
				post(TicketController.createTicket);
				path("{ticketId}", () -> {
					get(TicketController.getTicketById);
					put(TicketController.updateTicket);
					delete(TicketController.removeTicket);
				});
				//filtering tickets by criteria endpoints
				get("/types/{typeId}/users/{userId}", TicketController.getUserTicketsByType);
				get("/status/{statusId}/users/{userId}", TicketController.getUserTicketsByStatus);
				get("/users/{userId}", TicketController.getUserTickets);
			});
		
			// pictures endpoints
			before("pictures/*", LoginController.authorize);
			path("/pictures/user-profiles", () -> {
				get("/{userId}",PictureController.getProfPicture);
				post(PictureController.createProfPicture);
			});
			path("/pictures/ticket-receipts/{ticketId}", () -> {
				get(PictureController.getReceiptPicture);
				post(PictureController.createReceiptPicture);
			});
			

		});
		
	}

}
