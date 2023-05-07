package com.example.repositories;

import com.example.models.RouteLocationVisit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteLocationVisitRepository extends JpaRepository<RouteLocationVisit, Long> {
    
}
