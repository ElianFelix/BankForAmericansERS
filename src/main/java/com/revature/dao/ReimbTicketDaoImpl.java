package com.revature.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.models.ReimbTicket;
import com.revature.models.User;
import com.revature.utils.DbConn;

public class ReimbTicketDaoImpl implements ReimbTicketDao{

	public static Logger logger = LoggerFactory.getLogger(ReimbTicketDaoImpl.class);
	
	private Connection conn;

	public ReimbTicketDaoImpl() {
		conn = DbConn.getDbConnection();
	}

	@Override
	public List<ReimbTicket> getTickets() {
		List<ReimbTicket> ticketList = new ArrayList<>();
		String query = String.format("SELECT * FROM \"Reimbursement\";");
		logger.info("Our query to retrieve all tickets: \n" + query);
		
		try (Statement stmt = conn.createStatement()) {
			ResultSet rSet = stmt.executeQuery(query);
			
			
			while (rSet.next()) {
				ReimbTicket rTicket = new ReimbTicket(rSet.getInt("ReimbId"),
										rSet.getDouble("Amount"),
										rSet.getString("Desc"),
										rSet.getInt("OwnerId"),
										rSet.getInt("ResolverId"),
										rSet.getInt("StatusID"),
										rSet.getInt("TypeID"));
										
				ticketList.add(rTicket);
			}
			
			return ticketList;
		} catch (SQLException e) {
			System.out.println(e.getStackTrace());
		}
		
		return null;
	}
	
	@Override
	public List<ReimbTicket> getFilteredTickets(String filter, String filterVal, String userId) {
		List<ReimbTicket> ticketList = new ArrayList<>();
		String query;
		
		if (filter == null) {
			query = String.format("SELECT * FROM \"Reimbursement\" WHERE \"OwnerId\" = %s", 
					userId);
		} else {
			query = String.format("SELECT * FROM \"Reimbursement\" WHERE \"OwnerId\" = %s AND \"%s\" = %s;", 
					userId,
					filter,
					filterVal);
		}
		
		logger.info("Our query to retrieve all tickets: \n" + query);
		
		try (Statement stmt = conn.createStatement()) {
			ResultSet rSet = stmt.executeQuery(query);
			
			
			while (rSet.next()) {
				ReimbTicket rTicket = new ReimbTicket(rSet.getInt("ReimbId"),
										rSet.getDouble("Amount"),
										rSet.getString("Desc"),
										rSet.getInt("OwnerId"),
										rSet.getInt("ResolverId"),
										rSet.getInt("StatusID"),
										rSet.getInt("TypeID"));
										
				ticketList.add(rTicket);
			}
			
			return ticketList;
		} catch (SQLException e) {
			System.out.println(e.getStackTrace());
		}
		
		return null;
	}

	@Override
	public int create(ReimbTicket ticket) {
		String query = String.format("INSERT INTO public.\"Reimbursement\" (\"ReimbId\", \"Amount\", \"Desc\", \"OwnerId\", \"StatusId\", \"TypeId\")\n "
				+ "VALUES (DEFAULT, %f, '%s', %d, 1, %d) RETURNING \"ReimbId\";",
				ticket.getAmount(),
				ticket.getDescription(),
				ticket.getStartedBy(),
				ticket.getTypeID());
		logger.info("Our query to create ticket: \n" + query);
		
		try (Statement stmt = conn.createStatement()) {
			ResultSet rSet = stmt.executeQuery(query);
			while (rSet.next())
				return rSet.getInt(1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}
		
		return 0;
	}

	@Override
	public ReimbTicket getTicketById(int reimbId) {
		ReimbTicket ticket = new ReimbTicket();
		String query = String.format("SELECT * FROM \"Reimbursement\" WHERE \"ReimbId\" = '%d';", reimbId);
		logger.info("Our query to retrieve ticket: \n" + query);
		
		try (Statement stmt = conn.createStatement()) {
			ResultSet rSet = stmt.executeQuery(query);
			
			while (rSet.next()) {
				ticket.setReimbId(rSet.getInt("ReimbId"));
				ticket.setAmount(rSet.getDouble("Amount"));
				ticket.setDescription(rSet.getString("Desc"));
				ticket.setStartedBy(rSet.getInt("OwnerId"));
				ticket.setResolver(rSet.getInt("ResolverId"));
				ticket.setStatusID(rSet.getInt("StatusID"));
				ticket.setTypeID(rSet.getInt("TypeID"));
			}
			
			return ticket;
		} catch (SQLException e) {
			System.out.println(e.getStackTrace());
		}
		
		return null;
	}

	@Override
	public boolean updateTicket(ReimbTicket ticket) {
		int updatedId = 0;
		String query = String.format("UPDATE public.\"Reimbursement\" SET (\"Resolved\", \"ResolverId\", \"StatusId\") =\n"
				+ "    (current_timestamp, %d, %d) WHERE \"ReimbId\" = %d RETURNING \"ReimbId\";", 
				ticket.getResolver(),
				ticket.getStatusID(),
				ticket.getReimbId());
		logger.info("Our query to update ticket: \n" + query);
		
		try (Statement stmt = conn.createStatement()) {
			ResultSet rSet = stmt.executeQuery(query);
			while (rSet.next())
				updatedId = rSet.getInt(1);
			return updatedId == ticket.getReimbId();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}
		
		return false;
	}

	@Override
	public boolean deleteTicketById(int reimbId) {
		int deletedId = 0;
		String query = String.format("DELETE FROM \"Reimbursement\" WHERE \"ReimbId\" = %d RETURNING \"ReimbId\";", reimbId);
		logger.info("Our query to delete user: \n" + query);
		
		try (Statement stmt = conn.createStatement()) {
			ResultSet rSet = stmt.executeQuery(query);
			
			while (rSet.next()) {
				deletedId = rSet.getInt(1);
			}
			
			return deletedId == reimbId;
		} catch (SQLException e) {
			System.out.println(e.getStackTrace());
		}
		
		return false;
	}
	
}
