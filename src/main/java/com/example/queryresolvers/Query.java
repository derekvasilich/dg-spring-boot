/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.queryresolvers;

import com.example.models.Customer;
import com.example.models.User;
import com.example.models.Vehicle;
import com.example.repositories.CustomerRepository;
import com.example.repositories.UserRepository;
import com.example.repositories.VehicleRepository;

import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.relay.Connection;
import graphql.relay.SimpleListConnection;
import graphql.schema.DataFetchingEnvironment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 *
 * @author derek
 */
@Component
public class Query implements GraphQLQueryResolver {

	@Autowired
	VehicleRepository vehicleRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	CustomerRepository customerRepository;

	public Iterable<Vehicle> allVehicles() { 
		return vehicleRepository.findAll(); 
	}

	public Connection<Vehicle> allVehiclesPaginated(int page, int size, DataFetchingEnvironment env) { 
		Page<Vehicle> vehiclePage = vehicleRepository.findAll(PageRequest.of(page, size));
		SimpleListConnection<Vehicle> connectionList = new SimpleListConnection<>(vehiclePage.toList());
		Connection<Vehicle> connection = connectionList.get(env);
		return connection; 
	}

	public Vehicle getVehicleById(Long id) { 
		return vehicleRepository.findById(id).orElseThrow(null); 
	}

	public User getProfile() { 
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String email = userDetails.getUsername();
		return userRepository.findByEmail(email).orElseThrow();
	}	

	public Iterable<Customer> allCustomers() {
		return customerRepository.findAll();
	}
	
}
