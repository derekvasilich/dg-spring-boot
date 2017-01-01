package com.example.models;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(
    name = "route_location_visits", 
    uniqueConstraints = { 
        @UniqueConstraint(columnNames = { "routeId", "quoteId", "userId" }) 
    }
)
public class RouteLocationVisit {    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
    public Long id;
    @Column(nullable = false)
    public Long routeId;
    @Column(nullable = false)
    public Long quoteId;
    @Column(nullable = false)
    public Long userId;
    @Column(nullable = false)
    public Instant visitedAt;

    @ManyToOne
	@JsonIgnore
	@JoinColumn(name = "routeId", insertable = false, updatable = false)
    public Route route;

    public RouteLocationVisit() {
        this.visitedAt = Instant.now();        
    }

    public RouteLocationVisit(Long routeId, Long quoteId, Long userId) {
        this.routeId = routeId;
        this.quoteId = quoteId;
        this.userId = userId;
        this.visitedAt = Instant.now();
    }
}
