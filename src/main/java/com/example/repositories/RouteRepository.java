package com.example.repositories;

import com.example.models.Route;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends PagingAndSortingRepository<Route, Long> {

}
