package com.jonathanhuertas.taskmanager2.repositories;

import com.jonathanhuertas.taskmanager2.domain.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {

    //gives us methods out of box CRUD operations

    //JPA gives you a way to find by attributes related to the Object
    Project findByProjectIdentifier(String projectId);

    @Override
    Iterable<Project> findAll();
}
