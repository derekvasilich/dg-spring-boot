package com.example.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="contacts")
public class Contact {
 
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	public Long id;

	public String address_1;
	public String city;
	public String province;
	public String postal;
	public String country;

    public String phone;
	public String mobile;

	public Float lat;
	public Float lng;    

}