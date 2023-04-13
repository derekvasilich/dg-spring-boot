/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.queryresolvers;

import com.example.models.Description;
import com.example.models.Vehicle;
import com.example.repositories.DescriptionRepository;

import graphql.kickstart.tools.GraphQLResolver;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author derek
 */
@Component
public class VehicleResolver implements GraphQLResolver<Vehicle> {
	
	@Autowired
	DescriptionRepository descriptionRepo;
	
	public Optional<Description> getDescription(Vehicle vehicle) {
		return descriptionRepo.findByVehicleId(vehicle.id);
	}
	
}
