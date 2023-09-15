package com.revature.services;

import java.util.List;

import com.revature.dao.ReimbTicketDao;
import com.revature.dao.ReimbTicketDaoImpl;
import com.revature.dao.UserDao;
import com.revature.dao.UserDaoImpl;
import com.revature.models.ReimbTicket;
import com.revature.models.User;

public class ReimbTicketServiceImpl implements ReimbTicketService {

	private ReimbTicketDao ticketDao;
	private UserDao userDao;
	
	public ReimbTicketServiceImpl() {
		ticketDao = new ReimbTicketDaoImpl(); 
		userDao = new UserDaoImpl();
	}
	
	@Override
	public List<ReimbTicket> getTickets() {
		return ticketDao.getTickets();
	}

	@Override
	public List<ReimbTicket> getFilteredTickets(String filter, String filterVal, String userId) {
		return ticketDao.getFilteredTickets(filter, filterVal, userId);
	}

	@Override
	public int create(ReimbTicket ticket) {
		return ticketDao.create(ticket);
	}

	@Override
	public ReimbTicket getTicketById(int reimbId) {
		return ticketDao.getTicketById(reimbId);
	}

	@Override
	public boolean updateTicket(ReimbTicket ticket) {
		ReimbTicket currentTicket = ticketDao.getTicketById(ticket.getReimbId());
		
		if (ticket.getResolver() == currentTicket.getStartedBy() || currentTicket.getStatusID() != 1)
			return false;
		
		return ticketDao.updateTicket(ticket);
	}

	@Override
	public boolean deleteTicket(int reimbId) {
		return ticketDao.deleteTicketById(reimbId);
	}

}
