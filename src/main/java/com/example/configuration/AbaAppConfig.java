package com.example.configuration;

public class AbaAppConfig {
    public static final String companyTable = "customers";

    public static final String companyNameColumn = "company_name";
    public static final String truckAbbrevColumn = "truck_abbrev";
    public static final String legalNameColumn = "company_name";

    public static final String namedQuery1Name = "Company.findByTruckAbbrev";
    public static final String namedQuery1Query = "SELECT u FROM Company u WHERE u.truckAbbrev = :truckAbbrev";

    public static final String namedQuery2Name = "Company.findByTruckAbbrev.count";
    public static final String namedQuery2Query = "SELECT count(u) FROM Company u WHERE u.truckAbbrev = :truckAbbrev";
}
