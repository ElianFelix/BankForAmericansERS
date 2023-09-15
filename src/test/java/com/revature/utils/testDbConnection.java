package com.revature.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class testDbConnection {
	
	public static Logger testDbLogger = LoggerFactory.getLogger(testDbConnection.class);
	
	@Test
	void checkConnectionStatus() {
		Connection conn;
		
		try {
			conn = DbConn.getDbConnection();
			System.out.println(testDbLogger.isDebugEnabled());
			testDbLogger.info(conn.getMetaData().toString());
			System.out.println(conn.getMetaData().getURL());
			assertEquals(true, conn.isValid(0));
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());;
		}
	}

}
