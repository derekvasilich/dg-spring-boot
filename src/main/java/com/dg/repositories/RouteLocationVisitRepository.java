package com.dg.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dg.models.RouteLocationVisit;

@Repository
public interface RouteLocationVisitRepository extends JpaRepository<RouteLocationVisit, Long> {
    
}
