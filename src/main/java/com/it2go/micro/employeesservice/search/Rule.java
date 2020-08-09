package com.it2go.micro.employeesservice.search;

import lombok.Data;

@Data
public class Rule {

  private String field;
  private Operation op;
  private String data;
  // text, number, date
  private String type = "string";
}
