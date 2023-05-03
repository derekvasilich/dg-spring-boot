/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.repositories;

import com.example.models.Vehicle;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
/**
 *
 * @author derek
 */
@Repository
public interface VehicleRepository extends PagingAndSortingRepository<Vehicle, Long> {
	
}
