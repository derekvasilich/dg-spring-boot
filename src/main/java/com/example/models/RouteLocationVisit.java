package com.example.models;

import java.util.Date;

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
    public Long routeId;
    public Long quoteId;
    public Long userId;
    public Date visitedAt;

    @ManyToOne
	@JsonIgnore
	@JoinColumn(name = "routeId", insertable = false, updatable = false)
    public Route route;

    public RouteLocationVisit() {
        
    }

    public RouteLocationVisit(Long routeId, Long quoteId, Long userId) {
        this.routeId = routeId;
        this.quoteId = quoteId;
        this.userId = userId;
        this.visitedAt = new Date();
    }
}
