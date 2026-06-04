/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dg.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.dg.models.Vehicle;
/**
 *
 * @author derek
 */
@Repository
public interface VehicleRepository extends PagingAndSortingRepository<Vehicle, Long> {
	
}
