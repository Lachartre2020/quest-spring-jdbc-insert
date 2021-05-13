package com.wildcodeschool.wildandwizard.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.wildcodeschool.wildandwizard.entity.School;

import util.JdbcUtils;

public class SchoolRepository {

    private final static String DB_URL = "jdbc:mysql://localhost:3306/spring_jdbc_quest?serverTimezone=GMT";
    private final static String DB_USER = "h4rryp0tt3r";
    private final static String DB_PASSWORD = "Horcrux4life!";

    public School save(String name, Long capacity, String country) {
        // TODO : insert a new school in database
    	Connection connection = null;
    	PreparedStatement statement = null;
    	ResultSet generatedkeys= null;
    	
    	try {

    		connection = DriverManager.getConnection(
    				DB_URL, DB_USER, DB_PASSWORD
    				);
    				
    				
    		statement = connection.prepareStatement(
    				"INSERT INTO school (name, capacity, country) VALUES (?,?,?);",
    				Statement.RETURN_GENERATED_KEYS
    				);
    				
    		statement.setString(1, name);
    		statement.setLong(2,capacity);
    		statement.setString(3,country);
    		
    		if (statement.executeUpdate() != 1) {
    			throw new SQLException("failed to insert data");
    		}
    		
    		generatedkeys = statement.getGeneratedKeys();
    		
    		if (generatedkeys.next()) {
    			Long id = generatedkeys.getLong(1);
    			return new School(id, name, capacity, country);
    		}else {
    			throw new SQLException("failed to get inserted id");
    		}
   		
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}finally {
    		JdbcUtils.closeResultSet(generatedkeys);
    		JdbcUtils.closeStatement(statement);
    		JdbcUtils.closeConnection(connection);
    	}
    	
    	
    	return null;
    }
}
