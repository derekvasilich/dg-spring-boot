/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.repositories;

import com.example.models.Description;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author derek
 */
@Repository
public interface DescriptionRepository extends JpaRepository<Description, Long> {

	Optional<Description> findByVehicleId(Long id);
	
}