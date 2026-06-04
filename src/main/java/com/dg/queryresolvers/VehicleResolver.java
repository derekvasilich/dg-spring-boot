/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dg.queryresolvers;

import graphql.kickstart.tools.GraphQLResolver;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dg.models.Company;
import com.dg.models.Description;
import com.dg.models.Vehicle;
import com.dg.repositories.CompanyRepository;
import com.dg.repositories.DescriptionRepository;

/**
 *
 * @author derek
 */
@Component
public class VehicleResolver implements GraphQLResolver<Vehicle> {
	
	@Autowired
	DescriptionRepository descriptionRepo;

	@Autowired
	CompanyRepository companyRepo;
	
	public Optional<Description> getDescription(Vehicle vehicle) {
		return descriptionRepo.findByVehicleId(vehicle.id);
	}

	public Optional<Company> getCompany(Vehicle vehicle) {
		return companyRepo.findById(vehicle.company_id);
	}	
	
}
