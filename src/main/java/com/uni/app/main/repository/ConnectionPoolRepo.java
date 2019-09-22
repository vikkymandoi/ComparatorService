package com.uni.app.main.repository;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConnectionPoolRepo {
	@Bean(name="sitDataSource")
	@ConfigurationProperties(prefix = "spring.sit.datasource")
	public DataSource sitDataSource() {
	    return DataSourceBuilder.create().build();
	}

	@Bean(name="uatDataSource")
	@ConfigurationProperties(prefix = "spring.uat.datasource")
	public DataSource uatDataSource() {
	    return DataSourceBuilder.create().build();
	}
	
	@Bean(name="prodDataSource")
	@ConfigurationProperties(prefix = "spring.prod.datasource")
	public DataSource prodDataSource() {
	    return DataSourceBuilder.create().build();
	}
}
