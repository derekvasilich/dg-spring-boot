/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.xml.bind.DatatypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author derek
 */
public class Sha1PasswordEncoder implements PasswordEncoder {

    @Autowired
    private Environment environment;	
	
	private String sha1(String input) {
		String sha1 = null;
		try {
			MessageDigest msdDigest = MessageDigest.getInstance("SHA-1");
			msdDigest.update(input.getBytes("UTF-8"), 0, input.length());
			sha1 = DatatypeConverter.printHexBinary(msdDigest.digest());
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
			System.out.println("SHA-1 failed: "+e.getMessage());
		}
		return sha1;
	}	

	@Override
	public String encode(CharSequence cs) {
		String salt = environment.getProperty("app.security-salt");
		String str = salt + cs;
		String pwd = sha1(str);
		return pwd;
	}

	@Override
	public boolean matches(CharSequence cs, String string) {
		String encoded = encode(cs);
		return string != null && string.compareToIgnoreCase(encoded) == 0;
	}
	
}
