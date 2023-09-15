package com.revature.dao;

import java.util.List;

import com.revature.models.ReimbTicket;

public interface ReimbTicketDao {
	
	List<ReimbTicket> getTickets();
	
	List<ReimbTicket> getFilteredTickets(String filer, String filterVal, String userId);
	
	int create(ReimbTicket ticket);
	
	ReimbTicket getTicketById(int reimbId);
	
	boolean updateTicket(ReimbTicket ticket);
	
	boolean deleteTicketById(int reimbId);
	
}
