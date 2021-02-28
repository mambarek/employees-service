package com.it2go.micro.employeesservice.web.controller;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled
public class TimeTest {

  @Test
  public void timeTest(){
    float aFloat = Float.parseFloat("4");
    System.out.println(aFloat);
    System.out.println(OffsetDateTime.now());
    String date = "1970-01-06";
    OffsetDateTime dateTime = LocalDate.parse(date).atTime(0, 0, 0).atOffset(ZonedDateTime.now().getOffset());
    //OffsetDateTime dateTime = LocalDate.parse(date).atTime(0, 0, 0).atOffset(ZoneOffset.UTC);
    System.out.println(dateTime);

    OffsetDateTime offsetDateTime = LocalDate.parse(date).atStartOfDay(ZoneId.systemDefault()).toOffsetDateTime();
    System.out.println(offsetDateTime);
  }
}
