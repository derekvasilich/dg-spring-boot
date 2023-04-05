/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.controllers;

import com.example.models.Vehicle;
import com.example.repositories.VehicleRepository;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author derek
 */
@RestController
@RequestMapping(path="/api/vehicles")
@CrossOrigin(origins = "http://localhost:3000")
public class VehicleController {
	
//	private static final Logger log = LoggerFactory.getLogger(VehicleController.class);
		
	@Autowired
	VehicleRepository vehicleRepo;
	
	@GetMapping(path="/{id}")
	public @ResponseBody Optional<Vehicle> getVehicle(@PathVariable Long id) {		
		return vehicleRepo.findById(id);
	}
		
	@GetMapping(path="/all")
	public @ResponseBody Iterable<Vehicle> getAllVehicles() {		
		return vehicleRepo.findAll();
	}
	
}
