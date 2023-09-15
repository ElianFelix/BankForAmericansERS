package com.revature.services;

import java.util.List;

import com.revature.models.ReimbTicket;

public interface ReimbTicketService {
	
	List<ReimbTicket> getTickets();
	
	List<ReimbTicket> getFilteredTickets(String filer, String filterVal, String userId);
	
	int create(ReimbTicket ticket);
	
	ReimbTicket getTicketById(int reimbId);
	
	boolean updateTicket(ReimbTicket ticket);
	
	boolean deleteTicket(int reimbId);
	
}
