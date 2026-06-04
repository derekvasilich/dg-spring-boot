package com.example.configuration;

public class DgAppConfig {
    public static final String companyTable = "companies";

    public static final String companyNameColumn = "name";
    public static final String truckAbbrevColumn = "omvic";
    
    public static final String legalNameColumn = "legal_name";

    public static final String namedQuery1Name = "Company.findByTruckAbbrev";
    public static final String namedQuery1Query = "SELECT u FROM Company u WHERE 1 = 0";

    public static final String namedQuery2Name = "Company.findByTruckAbbrev.count";
    public static final String namedQuery2Query = "SELECT count(u) FROM Company u WHERE 1 = 0";
}