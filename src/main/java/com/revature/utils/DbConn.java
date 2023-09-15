package com.revature.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbConn {
	
	public static Logger logger = LoggerFactory.getLogger(DbConn.class);
	
	public static Connection getDbConnection() {
		Connection connection = null;
		Properties connProps = new Properties();
		
		
		try {
			FileInputStream PropsFile = new FileInputStream("src/main/java/com/revature/db_env.properties");
			connProps.load(PropsFile);
			PropsFile.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String connPath = String.format("jdbc:%s://%s:%s/%s",
									connProps.get("driver"),
									connProps.get("server"),
									connProps.get("port"),
									connProps.get("dbName"));

		try {
			logger.debug("Stablishing connection" + connPath);
			connection = DriverManager.getConnection(connPath, connProps);
		} catch (SQLException e) {
			// prints out a debug statement with the connection path
			logger.debug("DB Connection couldn't be stablished at path: " + connPath);
		}
		
		return connection;
	}
	
}
