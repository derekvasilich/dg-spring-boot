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

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author derek
 */
@Entity
@Table(name="users")
@NamedQueries({
	@NamedQuery(
		name = "User.findByEmail", 
		query = "SELECT u FROM User u WHERE u.email = :email"
	),
	@NamedQuery(
		name = "User.findByEmail.count", 
		query = "SELECT count(u) FROM User u WHERE u.email = :email"
	),
	@NamedQuery(
		name = "User.existsByEmail", 
		query = "SELECT CASE WHEN COUNT(u) > 0 THEN 'true' ELSE 'false' END FROM User u WHERE u.email = :email"
	),
	@NamedQuery(
		name = "User.existsByEmail.count", 
		query = "SELECT count(u) FROM User u WHERE u.email = :email"
	)
})
public class User implements Serializable {
	
	public static final String DEFAULT_ROLE = "user";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	public Long id;
	public String email;
	public String role;
	@JsonIgnore()
	private String password;
	
	@Column(name="first_name")
	public String firstName;
	@Column(name="last_name")
	public String lastName;
	
	public User() { }

	public User(String  email, String firstName, String lastName, String encodedPassword) {
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = encodedPassword;
		this.role = User.DEFAULT_ROLE;
	}

	public String getPassword() {
		return password;
	}
}
