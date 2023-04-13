/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.queryresolvers;

import com.example.models.Vehicle;
import com.example.repositories.VehicleRepository;

import graphql.kickstart.tools.GraphQLQueryResolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author derek
 */
@Component
public class Query implements GraphQLQueryResolver {

	@Autowired
	VehicleRepository vehicleRepository;
	
	public Iterable<Vehicle> allVehicles() { 
		return vehicleRepository.findAll(); 
	}
	
	public Vehicle getVehicleById(Long id) { 
		return vehicleRepository.findById(id).orElseThrow(null); 
	}
	
}
