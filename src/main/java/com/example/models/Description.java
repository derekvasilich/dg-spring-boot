/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.models;

import com.example.converters.HashMapConverter;
import com.fasterxml.jackson.databind.JsonNode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Convert;

/**
 *
 * @author derek
 */
@Entity
@Table(name="descriptions")
public class Description {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	public Long id;		
	public String vin;
	public Long style_id;
	
	@Convert(converter = HashMapConverter.class)
    public JsonNode description;
	
	@OneToOne
	@JoinColumn(name = "vehicle_id")
	public Vehicle vehicle;
}
