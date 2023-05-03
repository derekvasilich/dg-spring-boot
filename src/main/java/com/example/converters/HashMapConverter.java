/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

/**
 *
 * @author derek
 */
@Component
@Converter
@Configurable
public class HashMapConverter implements AttributeConverter<JsonNode, String> {

	private static final Logger log = LoggerFactory.getLogger(HashMapConverter.class);
	
    private static ObjectMapper objectMapper; 

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper){
        HashMapConverter.objectMapper = objectMapper;
    }	
	
    @Override
    public String convertToDatabaseColumn(JsonNode customerInfo) {

        String customerInfoJson = null;
        try {
            customerInfoJson = objectMapper.writeValueAsString(customerInfo);
        } catch (final JsonProcessingException e) {
            log.error("JSON writing error: " + e.getMessage());
        }

        return customerInfoJson;
    }

    @Override
    public JsonNode convertToEntityAttribute(String customerInfoJSON) {

        JsonNode customerInfo = null;
        try {
            customerInfo = objectMapper.readValue(customerInfoJSON, JsonNode.class);
        } catch (final IOException e) {
            log.error("JSON reading error: " + e.getMessage());
        }

        return customerInfo;
    }

}