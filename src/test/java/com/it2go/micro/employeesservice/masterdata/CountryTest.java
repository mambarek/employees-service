package com.it2go.micro.employeesservice.masterdata;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class CountryTest {

    @Test
    void countryComparator() {
    }

    @Test
    void localeCollatorComparator() {
    }

    @Test
    void getAllCountries() {
        List<Country> allCountries = Country.getAllCountries(Locale.GERMANY);
        allCountries.forEach(System.out::println);
    }
}
