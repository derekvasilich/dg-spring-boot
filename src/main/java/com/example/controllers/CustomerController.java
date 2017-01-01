/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.controllers;

import com.example.models.Customer;
import com.example.repositories.CustomerRepository;
import com.example.response.MessageResponse;

import java.util.NoSuchElementException;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<?> getCustomer(@PathVariable Long id) {	
		try {	
			Customer cust = customerRepo.findById(id).orElseThrow();
			return new ResponseEntity<Customer>(cust, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(new MessageResponse("Customer not found!"));
		}
	}


	@GetMapping(path = "")
	public @ResponseBody Iterable<Customer> getAllCustomers() {		
		return customerRepo.findAll();
	}

}
