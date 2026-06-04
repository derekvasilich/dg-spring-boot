package com.dg.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dg.models.Customer;

/**
 *
 * @author derek
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}