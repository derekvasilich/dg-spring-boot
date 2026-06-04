package com.dg.controllers;

import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.dg.models.Route;
import com.dg.models.RouteLocationVisit;
import com.dg.repositories.RouteLocationVisitRepository;
import com.dg.repositories.RouteRepository;
import com.dg.response.MessageResponse;

/**
 *
 * @author derek
 */
@RestController
@RequestMapping(path="/api/routes")
@CrossOrigin(origins = "http://localhost:3000")
public class RouteController {
	
	private static final Logger log = LoggerFactory.getLogger(RouteController.class);
		
	@Autowired
	RouteRepository routeRepo;

	@Autowired
	RouteLocationVisitRepository visitRepo;
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<?> getRoute(@PathVariable Long id) {		
		try {
			Route route = routeRepo.findById(id).orElseThrow();
			return new ResponseEntity<Route>(route, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(new MessageResponse<String>("Route not found!"));
		}
	}


	@GetMapping(path = "")
	public @ResponseBody Iterable<Route> getAllRoutes() {		
		return routeRepo.findAll();
	}

	@PostMapping(path = "/visit")
	public @ResponseBody RouteLocationVisit saveLocationVisit(@RequestBody RouteLocationVisit visit) {
		try {
			RouteLocationVisit newVisit = visitRepo.save(visit);
			return newVisit;
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}

	@GetMapping(path = "/paginated")
	public @ResponseBody Page<Route> getAllRoutesPaginated(Pageable p) {	
		return routeRepo.findAll(p);
	}    

}
