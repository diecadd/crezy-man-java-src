package com.crezyman.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.sql.DataSource;

import com.crezyman.dao.AbstractBaseDaoImpl;

import java.util.Properties;

public class DataSourceUtil {
	private static DataSource dataSource;
	
	private static String driver;
	private static String url;
	private static String user;
	private static String password;
	
	static {
		init();
	}
	
	public static void init(){
		Properties params=new Properties();
		String configFile = AbstractBaseDaoImpl.getDomainPackage("crezyman.databaseConfigFileName").trim();
		
		
		InputStream is=DataSourceUtil.class.getClassLoader().getResourceAsStream(configFile);
		try {
			params.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		driver=params.getProperty("crezyman.driver").trim();
		url=params.getProperty("crezyman.url").trim();
		user=params.getProperty("crezyman.username").trim();
		password=params.getProperty("crezyman.password").trim();
	}   

	public static Connection openConnection() throws SQLException {
		Connection connection = null;
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return connection;
	}

	public static void closeConnection(Connection connection) {
		try {
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
