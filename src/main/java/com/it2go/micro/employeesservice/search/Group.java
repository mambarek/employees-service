package com.it2go.micro.employeesservice.search;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class Group implements Serializable {

  private GroupOperation groupOp;
  private List<Rule> rules;
  private List<Group> groups;
}
