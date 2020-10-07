package com.it2go.micro.employeesservice.services.clients;

import com.it2go.micro.projectservice.domain.SearchResultProjectTableItem;
import com.it2go.util.jpa.search.SearchTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProjectServiceClient {

  @Value("${projectservice.search.url}")
  private String projectResourceUrl;

  public SearchResultProjectTableItem searchProjects(SearchTemplate searchTemplate){

    RestTemplate restTemplate = new RestTemplate();
    HttpEntity<SearchTemplate> httpEntity = new HttpEntity<>(searchTemplate);

    SearchResultProjectTableItem searchResultResponse = restTemplate
        .postForObject(projectResourceUrl, httpEntity, SearchResultProjectTableItem.class);

    return searchResultResponse;
  }
}
