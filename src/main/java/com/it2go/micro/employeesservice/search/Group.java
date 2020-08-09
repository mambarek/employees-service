package com.it2go.micro.employeesservice.search;

import java.util.List;
import lombok.Data;

@Data
public class Group {

  private GroupOperation groupOp;
  private List<Rule> rules;
  private List<Group> groups;
}
