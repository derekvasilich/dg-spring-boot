/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.controllers;

import com.example.models.Customer;
import com.example.repositories.CustomerRepository;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

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
@RequestMapping(path="/api/customers")
@CrossOrigin(origins = "http://localhost:3000")
public class CustomerController {
	
//	private static final Logger log = LoggerFactory.getLogger(VehicleController.class);
		
	@Autowired
	CustomerRepository customerRepo;
	
	@GetMapping(path = "/{id}")
	public @ResponseBody Customer getCustomer(@PathVariable Long id) {		
		return customerRepo.findById(id).orElseThrow();
	}


	@GetMapping(path = "")
	public @ResponseBody Iterable<Customer> getAllCustomers() {		
		return customerRepo.findAll();
	}

}
