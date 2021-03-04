package com.it2go.micro.employeesservice.persistence.jpa.repositories;

import com.it2go.micro.employeesservice.persistence.jpa.entities.ProjectEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<ProjectEntity, Long> {

  Optional<ProjectEntity> findByPublicId(UUID publicId);

  @Query("select p from ProjectEntity p")
  List<ProjectEntity> findAllProjects();
}
