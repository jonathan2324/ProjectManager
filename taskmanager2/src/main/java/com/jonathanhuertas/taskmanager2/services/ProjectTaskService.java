package com.jonathanhuertas.taskmanager2.services;

import com.jonathanhuertas.taskmanager2.domain.ProjectTask;
import com.jonathanhuertas.taskmanager2.repositories.BacklogRepository;
import com.jonathanhuertas.taskmanager2.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    public ProjectTask addProjectTask(){

        //PTs -> to be added to a specific project, so not null project -> means backlog exists
        //set the backlog to the project task
        //we want our project sequence to be  IDPRO-1 IDPRO-2 and we want sequence to keep growing aka if one is deleted we dont add it again
        //update the backlog sequence

        //set an initial priority when priority is null
        //set an initial status when status is null
        return null;
    }
}
