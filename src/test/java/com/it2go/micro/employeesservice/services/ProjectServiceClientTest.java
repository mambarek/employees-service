package com.it2go.micro.employeesservice.services;

import com.it2go.micro.employeesservice.services.clients.ProjectServiceClient;
import com.it2go.micro.projectservice.domain.SearchResultProjectTableItem;
import com.it2go.util.jpa.search.Group;
import com.it2go.util.jpa.search.Operation;
import com.it2go.util.jpa.search.Rule;
import com.it2go.util.jpa.search.RuleType;
import com.it2go.util.jpa.search.SearchTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProjectServiceClientTest {

  @Autowired
  ProjectServiceClient projectServiceClient;

  @Test
  void testSearch(){
    Rule rule = new Rule();
    rule.setField("name");
    //rule.setData("New Building");
    rule.setData("Building");
    rule.setOp(Operation.CONTAINS);
    rule.setType(RuleType.STRING);
    Group group = new Group();
    group.getRules().add(rule);

    SearchTemplate employeesSearchTemplate = new SearchTemplate();
    employeesSearchTemplate.setMaxResult(2);
    employeesSearchTemplate.setFilters(group);
    SearchResultProjectTableItem searchResultResponse = projectServiceClient.searchProjects(employeesSearchTemplate);
    System.out.println(searchResultResponse);
  }
}
