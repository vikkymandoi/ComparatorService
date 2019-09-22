package com.uni.app.main.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.EnumSet;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

public enum ConnPool {
	SIT {
		public Connection getConnection() throws SQLException {
			return sitDataSource.getConnection();
		}
	},
	UAT {
		public Connection getConnection() throws SQLException {
			return uatDataSource.getConnection();
		}
	},
	PROD {
		public Connection getConnection() throws SQLException {
			return uatDataSource.getConnection();
		}
	};
	
	abstract public Connection getConnection() throws SQLException;
	DataSource sitDataSource;
	DataSource uatDataSource;
	DataSource prodDataSource;
	
	private void setSitDataSource(DataSource sitDataSource) {
		this.sitDataSource = sitDataSource;
	}
	private void setUatDataSource(DataSource uatDataSource) {
		this.uatDataSource = uatDataSource;
	}
	public void setProdDataSource(DataSource prodDataSource) {
		this.prodDataSource = prodDataSource;
	}
	
	@Component
	@DependsOn({"sitDataSource","uatDataSource","prodDataSource"})
	public static class ConnectionBuilder {
		@Autowired
		@Qualifier("sitDataSource")
		private DataSource sitDataSource;
		
		@Autowired
		@Qualifier("uatDataSource")
		private DataSource uatDataSource;
		
		@Autowired
		@Qualifier("prodDataSource")
		private DataSource prodDataSource;
		
		@PostConstruct
		public void buildConnPool() {			
			for(ConnPool cPool: EnumSet.allOf(ConnPool.class)) {
				cPool.setSitDataSource(this.sitDataSource);
				cPool.setUatDataSource(this.uatDataSource);
				cPool.setProdDataSource(this.prodDataSource);
			}
		}
	}
}
