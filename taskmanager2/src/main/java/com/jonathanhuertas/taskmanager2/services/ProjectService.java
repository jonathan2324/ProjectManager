package com.jonathanhuertas.taskmanager2.services;

import com.jonathanhuertas.taskmanager2.domain.Backlog;
import com.jonathanhuertas.taskmanager2.domain.Project;
import com.jonathanhuertas.taskmanager2.exceptions.ProjectIdException;
import com.jonathanhuertas.taskmanager2.repositories.BacklogRepository;
import com.jonathanhuertas.taskmanager2.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
Here we connect to the CRUD Repository so that we have access to query the database
we use this file for logic and query the database to return the data to the controller
In the repository, we get access to basic crud operations, we added a few methods such as findByProjectIdentifier
but we have access to save(), delete()
 */

@Service
public class ProjectService {

    //wire up project repository-CRUD operations
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;


    public Project saveOrUpdateProject(Project project){

        try{
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());

            if(project.getId() == null){
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            }

            if(project.getId() != null){
                project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
            }



            return projectRepository.save(project);
        } catch(Exception err){
            throw new ProjectIdException("Project ID " + project.getProjectIdentifier().toUpperCase() + " already exists");
        }
    }

    public Project findProjectByIdentifier(String projectId){

        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if(project == null){
            throw new ProjectIdException("Project ID " + projectId + " does not exists");
        }

        return project;
    }

    //iterable-> something that returns a list, basic way to extract is to traverse
    //this iterable returns a json object
    public Iterable<Project> findAllProjects(){
        return projectRepository.findAll();
    }

    public void deleteProjectByIdentifier(String projectId){
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if(projectId == null){
            throw new ProjectIdException("Cannot delete project with id " + projectId + ". " + " This project does not exist");
        }

        projectRepository.delete(project);
    }
}
