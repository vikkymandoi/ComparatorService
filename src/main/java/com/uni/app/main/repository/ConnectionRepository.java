package com.uni.app.main.repository;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConnectionRepository {
	private static final Logger logger = LoggerFactory.getLogger(ConnectionRepository.class); 
	
	public Connection getConnection(String env) throws SQLException {
		return ConnPool.valueOf(env.toUpperCase()).getConnection();
	}
}
