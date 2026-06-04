package com.dg.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dg.models.Quote;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {

}