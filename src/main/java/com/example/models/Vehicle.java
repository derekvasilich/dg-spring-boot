/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author derek
 */
@Entity
@Table(name="vehicles")
@NamedQueries({
	@NamedQuery(name = "Vehicle.findByVin",	query = "SELECT v FROM Vehicle v WHERE v.vin = :vin")
})
public class Vehicle implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	public Long id;	
	public Long company_id;	
	public String vin;
	public String name;
	public Float price;
	
	@OneToMany(mappedBy="foreign_key")
	public Set<Attachment> attachments = new HashSet<>();
	
	@OneToOne(mappedBy="vehicle")
	public Description description;

	@OneToOne
	@JoinColumn(name = "company_id", insertable = false, updatable = false)
	public Company company;
}
