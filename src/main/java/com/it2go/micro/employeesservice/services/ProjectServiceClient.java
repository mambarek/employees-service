package com.it2go.micro.employeesservice.services;

import com.it2go.micro.projectservice.domain.SearchResultResponse;
import com.it2go.util.jpa.search.SearchTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProjectServiceClient {

  @Value("${projectservice.search.url}")
  private String projectResourceUrl;

  public SearchResultResponse searchProjects(SearchTemplate searchTemplate){

    RestTemplate restTemplate = new RestTemplate();
    HttpEntity<SearchTemplate> httpEntity = new HttpEntity<>(searchTemplate);

    SearchResultResponse searchResultResponse = restTemplate
        .postForObject(projectResourceUrl, httpEntity, SearchResultResponse.class);

    return searchResultResponse;
  }
}
