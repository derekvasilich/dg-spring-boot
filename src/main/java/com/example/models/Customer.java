package com.example.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="customers")
public class Customer {
 
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	public Long id;

	// ABA
	// @Column(name="company_name")
	// public String companyName;
	@Column(name="legal_name")
	public String legalName;

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
