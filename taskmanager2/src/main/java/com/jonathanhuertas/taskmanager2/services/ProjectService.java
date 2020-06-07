package com.jonathanhuertas.taskmanager2.services;

import com.jonathanhuertas.taskmanager2.domain.Project;
import com.jonathanhuertas.taskmanager2.exceptions.ProjectIdException;
import com.jonathanhuertas.taskmanager2.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    //wire up project repository-CRUD operations
    @Autowired
    private ProjectRepository projectRepository;


    public Project saveOrUpdateProject(Project project){

        try{
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
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
