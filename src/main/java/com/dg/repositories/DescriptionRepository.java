/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dg.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dg.models.Description;

/**
 *
 * @author derek
 */
@Repository
public interface DescriptionRepository extends JpaRepository<Description, Long> {

	Optional<Description> findByVehicleId(Long id);
	
}