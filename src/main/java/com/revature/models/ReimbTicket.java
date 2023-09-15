package com.revature.models;

import java.util.Objects;

public class ReimbTicket {
	// Initial attribute creation; Leaving everything public as a start
	private int reimbId;
	private double amount;
	private String description;
	private int startedBy;
	private int resolver;
	private int statusID;
	private int typeID;
	// TODO: Implement a picture class to work with profile pictures (optional functionality)
	// Picture Receipt;
	
	// No args constructor
	public ReimbTicket() {
		super();
	}
	
	// All args constructor (pictures of receipts will be added individually)
	public ReimbTicket(int reimbId, 
			double amount, 
			String description, 
			int startedBy, 
			int resolver,
			int statusID, 
			int typeID) {
		super();
		this.reimbId = reimbId;
		this.amount = amount;
		this.description = description;
		this.startedBy = startedBy;
		this.resolver = resolver;
		this.statusID = statusID;
		this.typeID = typeID;
	}

	public int getReimbId() {
		return reimbId;
	}

	public void setReimbId(int reimbId) {
		this.reimbId = reimbId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStartedBy() {
		return startedBy;
	}

	public void setStartedBy(int startedBy) {
		this.startedBy = startedBy;
	}

	public int getResolver() {
		return resolver;
	}

	public void setResolver(int resolver) {
		this.resolver = resolver;
	}

	public int getStatusID() {
		return statusID;
	}

	public void setStatusID(int statusID) {
		this.statusID = statusID;
	}

	public int getTypeID() {
		return typeID;
	}

	public void setTypeID(int typeID) {
		this.typeID = typeID;
	}

	@Override
	public int hashCode() {
		return Objects.hash(amount, description, reimbId, resolver, startedBy, statusID, typeID);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReimbTicket other = (ReimbTicket) obj;
		return Double.doubleToLongBits(amount) == Double.doubleToLongBits(other.amount)
				&& Objects.equals(description, other.description) && reimbId == other.reimbId
				&& Objects.equals(resolver, other.resolver) && Objects.equals(startedBy, other.startedBy)
				&& statusID == other.statusID && typeID == other.typeID;
	}

	@Override
	public String toString() {
		return "Reimbursement [reimbId=" + reimbId + ", amount=" + amount + ", description=" + description
				+ ", startedBy=" + startedBy + ", resolver=" + resolver + ", statusID=" + statusID + ", typeID="
				+ typeID + "]";
	}
	
	
}