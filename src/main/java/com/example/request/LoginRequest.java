/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.request;

/**
 *
 * @author derek
 */
public class LoginRequest {
	private String email;
	private String password;
	
	LoginRequest(String email, String password) {
		this.email = email;
		this.password = password;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getPassword() {
		return this.password;
	}
}
