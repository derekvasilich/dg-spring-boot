/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.models;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author derek
 */
@Entity
@Table(name="companies")
// ABA
// @Table(name="customers")
@NamedQueries({
	// ABA
	// @NamedQuery(
	// 	name = "Company.findByTruckAbbrev", 
	// 	query = "SELECT u FROM Company u WHERE u.truckAbbrev = :truckAbbrev"
	// ),
	// @NamedQuery(
	// 	name = "Company.findByTruckAbbrev.count", 
	// 	query = "SELECT count(u) FROM Company u WHERE u.truckAbbrev = :truckAbbrev"
	// )
})
public class Company implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	public Long id;
	// ABA
	// @Column(name="truck_abbrev")
	// public String truckAbbrev;
	public String type;
	@Column(name="name")
	// ABA
	// @Column(name="company_name")
	public String companyName;
	
	@Column(name="first_name")
	public String firstName;
	@Column(name="last_name")
	public String lastName;
	public String email;
	
	public String phone;
	public String mobile;
	public String address_1;
	public String city;
	public String province;
	public String postal;
	public String country;
	
	public Float lat;
	public Float lng;
}
