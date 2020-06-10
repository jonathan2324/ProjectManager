package com.jonathanhuertas.taskmanager2.repositories;

import com.jonathanhuertas.taskmanager2.domain.Backlog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BacklogRepository extends CrudRepository<Backlog, Long> {

    //custom function to find by fields on Backlog object
    Backlog findByProjectIdentifier(String Identifier);
}
