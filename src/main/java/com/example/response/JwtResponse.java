/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.response;

import java.util.List;

public class JwtResponse {

	private String token;
	private String type = "Bearer";
	private String username;
	private Long id;
	private List<String> roles;

	public JwtResponse(String jwt, String username, Long id, List<String> roles) {
		this.token = jwt;
		this.username = username;
		this.id = id;
		this.roles = roles;
	}
	
	public String getToken() {
		return this.token;
	}
	
	public String getType() {
		return this.type;
	}
	
	public String getUsername() {
		return this.username;
	}

	public Long getId() {
		return this.id;
	}
	
	public List<String> getRoles() {
		return this.roles;
	}
}