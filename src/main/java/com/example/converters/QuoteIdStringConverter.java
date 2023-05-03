package com.example.converters;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import com.example.models.Quote;
import com.example.repositories.QuoteRepository;

@Component
@Converter
@Configurable
public class QuoteIdStringConverter implements AttributeConverter<List<Quote>, String> {

    private static final Logger log = LoggerFactory.getLogger(HashMapConverter.class);
    
    private static QuoteRepository quoteRepository;

    @Autowired
    public void setQuoteRepository(QuoteRepository quoteRepository){
        QuoteIdStringConverter.quoteRepository = quoteRepository;
    }	

    @Override
    public String convertToDatabaseColumn(List<Quote> attribute) {
        return attribute.stream().map(quote -> quote.id.toString()).collect(Collectors.joining(","));
    }

    @Override
    public List<Quote> convertToEntityAttribute(String dbData) {
        List<Quote> quotes = Arrays.asList();
        if (dbData == null || dbData.isEmpty()) return quotes;
        try {
            String[] strIds = dbData.split(",");
            List<Long> ids = Arrays.asList(strIds).stream().map(Long::parseLong).collect(Collectors.toList());
            quotes = quoteRepository.findAllById(ids);    
        } catch (Exception e) {
            log.error("QuoteIdStringConverter error: " + e.getMessage());
        }
        return quotes;
    }
    
}
