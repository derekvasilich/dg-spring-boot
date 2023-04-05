/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.models;

import com.example.HashMapConverter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Convert;

import java.util.Map;

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
    public Map<String, Object> description;
	
	@OneToOne
	@JoinColumn(name = "vehicle_id")
	private Vehicle vehicle;
}
