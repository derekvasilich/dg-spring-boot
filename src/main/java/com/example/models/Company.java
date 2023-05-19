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

import com.example.configuration.AppConfig;

/**
 *
 * @author derek
 */
@Entity
@Table(name=AppConfig.companyTable)
@NamedQueries({
	@NamedQuery(
		name = AppConfig.namedQuery1Name, 
		query = AppConfig.namedQuery1Query
	),
	@NamedQuery(
		name = AppConfig.namedQuery2Name, 
		query = AppConfig.namedQuery2Query
	)
})
public class Company implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	public Long id;
	@Column(name=AppConfig.truckAbbrevColumn)
	public String truckAbbrev;
	public String type;

	@Column(name=AppConfig.companyNameColumn)
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
