package com.example.repositories;

import com.example.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author derek
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}