/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.xml.bind.DatatypeConverter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author derek
 */
public class ShaPasswordEncoder implements PasswordEncoder {

	private static final Logger log = LoggerFactory.getLogger(ShaPasswordEncoder.class);

	@Value("${app.security-salt}")
	private String securitySalt;

	@Value("${app.security-algorithm}")
	private String securityAlgorithm;

	private String sha(String input) {
		String sha = null;
		try {
			MessageDigest msdDigest = MessageDigest.getInstance(securityAlgorithm);
			msdDigest.update(input.getBytes("UTF-8"), 0, input.length());
			sha = DatatypeConverter.printHexBinary(msdDigest.digest());
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
			log.error("Password Encoding failed: {}", e.getMessage());
		}
		return sha;
	}	

	@Override
	public String encode(CharSequence cs) {
		String str = securitySalt + cs;
		return sha(str);
	}

	@Override
	public boolean matches(CharSequence cs, String string) {
		String encoded = encode(cs);
		return string != null && string.compareToIgnoreCase(encoded) == 0;
	}
	
}
