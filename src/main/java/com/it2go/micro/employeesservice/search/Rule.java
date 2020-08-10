package com.it2go.micro.employeesservice.search;

import lombok.Data;

@Data
public class Rule {

  private String field;
  private Operation op = Operation.EQUAL;
  private String data;
  private RuleType type = RuleType.STRING;
}
