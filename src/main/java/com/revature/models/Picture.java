package com.revature.models;

import java.io.File;
import java.util.Objects;

import io.javalin.http.UploadedFile;

// TODO: Optional feature defining picture (user profile and reimbursement receipt) file interactions
public class Picture {
	public int resourceId;
	public UploadedFile data;
	
	@Override
	public String toString() {
		return "Picture [resourceId=" + resourceId + ", data=" + data + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(data, resourceId);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Picture other = (Picture) obj;
		return Objects.equals(data, other.data) && resourceId == other.resourceId;
	}
}
