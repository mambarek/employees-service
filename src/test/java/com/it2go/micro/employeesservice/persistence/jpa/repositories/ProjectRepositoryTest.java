package com.it2go.micro.employeesservice.persistence.jpa.repositories;

import static org.junit.jupiter.api.Assertions.*;

import com.it2go.micro.employeesservice.mapper.ProjectMapper;
import com.it2go.micro.employeesservice.mapper.ProjectMapperImpl;
import com.it2go.micro.employeesservice.persistence.jpa.entities.ProjectEntity;
import com.it2go.micro.projectmanagement.domain.Project;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.*;

/**
 * created by mmbarek on 02.03.2021.
 */
@DataJpaTest
class ProjectRepositoryTest {

  @Autowired
  TestEntityManager entityManager;

  @Autowired
  ProjectRepository projectRepository;

  ProjectMapper projectMapper = new ProjectMapperImpl();

  @Test
  void findByPublicId() {
    ProjectEntity p1 = new ProjectEntity();
    p1.setPublicId(UUID.randomUUID());
    p1.setName("Project1");
    p1.setDescription("my project 1");

    entityManager.persist(p1);
    entityManager.flush();

    Optional<ProjectEntity> byPublicId = projectRepository.findByPublicId(p1.getPublicId());
    assertThat(byPublicId.isEmpty()).isFalse();
  }

  @Test
  void findAllProjects(){
    ProjectEntity p1 = new ProjectEntity();
    p1.setPublicId(UUID.randomUUID());
    p1.setName("Project1");
    p1.setDescription("my project 1");

    ProjectEntity p2 = new ProjectEntity();
    p2.setPublicId(UUID.randomUUID());
    p2.setName("Project2");
    p2.setDescription("my project 2");

    entityManager.persist(p1);
    entityManager.persist(p2);
    entityManager.flush();

    List<ProjectEntity> allProjects = projectRepository.findAllProjects();
    assertThat(allProjects).isNotNull();
    System.out.println(allProjects);
  }
}
