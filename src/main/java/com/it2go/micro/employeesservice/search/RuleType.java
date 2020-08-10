package com.it2go.micro.employeesservice.search;

public enum RuleType {
  STRING,
  NUMBER,
  DATE;

  public boolean equalsString(String value){
    if(value == null) return false;
    return this.name().equalsIgnoreCase(value) ;
  }
}
