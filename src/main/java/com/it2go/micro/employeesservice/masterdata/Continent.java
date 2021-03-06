package com.it2go.micro.employeesservice.masterdata;

import java.text.Collator;
import java.util.*;
import java.util.stream.Collectors;

public enum Continent {

    /*
    AF,Africa
    NA,North America
    OC,Oceania
    AN,Antarctica
    AS,Asia
    EU,Europe
    SA,South America
     */

    AFRICA("AF","Africa","AO", "BF", "BI", "BJ", "BW", "CD", "CF", "CG", "CI", "CM", "CV", "DJ", "DZ", "EG", "EH", "ER", "ET", "GA", "GH", "GM", "GN", "GQ", "GW", "KE", "KM", "LR", "LS", "LY", "MA", "MG", "ML", "MR", "MU", "MW", "MZ", "NA", "NE", "NG", "RE", "RW", "SC", "SD", "SH", "SL", "SN", "SO", "ST", "SZ", "TD", "TG", "TN", "TZ", "UG", "YT", "ZA", "ZM", "ZW"),
    ANTARCTICA("AN","Antarctica","AQ", "BV", "GS", "HM", "TF"),
    ASIA("AS","Asia","AE", "AF", "AM", "AP", "AZ", "BD", "BH", "BN", "BT", "CC", "CN", "CX", "CY", "GE", "HK", "ID", "IL", "IN", "IO", "IQ", "IR", "JO", "JP", "KG", "KH", "KP", "KR", "KW", "KZ", "LA", "LB", "LK", "MM", "MN", "MO", "MV", "MY", "NP", "OM", "PH", "PK", "PS", "QA", "SA", "SG", "SY", "TH", "TJ", "TL", "TM", "TW", "UZ", "VN", "YE"),
    EUROPE("EU","Europe", "AD", "AL", "AT", "AX", "BA", "BE", "BG", "BY", "CH", "CZ", "DE", "DK", "EE", "ES", "FI", "FO", "FR", "GB", "GG", "GI", "GR", "HR", "HU", "IE", "IM", "IS", "IT", "JE", "LI", "LT", "LU", "LV", "MC", "MD", "ME", "MK", "MT", "NL", "NO", "PL", "PT", "RO", "RS", "RU", "SE", "SI", "SJ", "SK", "SM", "TR", "UA", "VA"),
    NORTH_AMERICA("NA","North America","AG", "AI", "AN", "AW", "BB", "BL", "BM", "BS", "BZ", "CA", "CR", "CU", "DM", "DO", "GD", "GL", "GP", "GT", "HN", "HT", "JM", "KN", "KY", "LC", "MF", "MQ", "MS", "MX", "NI", "PA", "PM", "PR", "SV", "TC", "TT", "US", "VC", "VG", "VI"),
    OCEANIA("OC","Oceania","AS", "AU", "CK", "FJ", "FM", "GU", "KI", "MH", "MP", "NC", "NF", "NR", "NU", "NZ", "PF", "PG", "PN", "PW", "SB", "TK", "TO", "TV", "UM", "VU", "WF", "WS"),
    SOUTH_AMERICA("SA","South America","AR", "BO", "BR", "CL", "CO", "EC", "FK", "GF", "GY", "PE", "PY", "SR", "UY", "VE");

    private final String code;
    private final String name;
    private final String[] countryCodes;

    Continent(String code, String name, String... ccodes){
        this.code = code;
        this.name = name;
        this.countryCodes = ccodes;
    }

    public String getCode(){
        return code;
    }

    public String getName(){
        return name;
    }

    public String[] getCountryCodes() {
        return countryCodes;
    }

    public static String getLocalizedNameFor(Continent continent, Locale locale){
        String resLabel;

        try {
            String baseName = "com/it2go/micro/employeesservice/masterdata/i18n/continents";
            ResourceBundle resourceBundle = ResourceBundle.getBundle(baseName, locale);
            resLabel = resourceBundle.getString(continent.toString());
        }catch (Exception e){
            e.printStackTrace();
            resLabel = continent.name;
        }

        return resLabel;
    }

    public List<Country> getCountries(Locale locale){
        return Arrays.stream(countryCodes).map(code ->
                new Country(code,new Locale("", code).getDisplayCountry(locale)))
                .sorted(countryComparator(locale))
                .collect(Collectors.toList());
    }

    private Comparator<Country> countryComparator(Locale locale) {
        return Comparator.comparing(Country::getName, localeCollatorComparator(locale));
    }

    private Comparator<String> localeCollatorComparator(Locale locale) {
        return Collator.getInstance(locale)::compare;
    }

    public static Continent getContinentContainingCountry(String countryCode) {
        if(Objects.isNull(countryCode) || countryCode.length() == 0)
            return null;

        return Arrays.stream(Continent.values()).filter(continent -> Arrays.asList(continent.getCountryCodes())
                .contains(countryCode.toUpperCase())).findFirst().orElse(EUROPE);
    }

    public static Continent getContinentWithCode(String code) {
        if(Objects.isNull(code) || code.length() == 0)
            return null;

        return Arrays.stream(Continent.values()).filter(continent -> continent.getCode().equals(code.toUpperCase()))
                .findFirst().orElse(EUROPE);
    }
}
