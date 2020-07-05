package com.it2go.micro.employeesservice.masterdata;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Proxy;
import java.text.Collator;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class Country implements Comparable<Country> {
    private final String code;
    private final String name;

    @Override
    public int compareTo(Country o) {
        return Collator.getInstance(Locale.GERMANY).compare(name, o.name);
    }

    @Override
    public String toString() {
        return "[" + code + "] " + name;
    }

    public static Comparator<Country> countryComparator(Locale locale) {
        return Comparator.comparing(Country::getName, localeCollatorComparator(locale));
    }

    public static Comparator<String> localeCollatorComparator(Locale locale) {
        return Collator.getInstance(locale)::compare;
    }

    public static List<Country> getAllCountries(Locale locale) {
        return Arrays.stream(Continent.values()).flatMap(cont -> cont.getCountries(locale)
                .stream()).sorted(countryComparator(locale)).collect(Collectors.toList());
    }

}
