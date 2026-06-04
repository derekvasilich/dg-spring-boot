package com.dg.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.dg.models.Route;

@Repository
public interface RouteRepository extends PagingAndSortingRepository<Route, Long> {

}
