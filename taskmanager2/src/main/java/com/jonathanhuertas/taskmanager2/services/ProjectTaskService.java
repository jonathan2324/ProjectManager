package com.jonathanhuertas.taskmanager2.services;

import com.jonathanhuertas.taskmanager2.domain.Backlog;
import com.jonathanhuertas.taskmanager2.domain.Project;
import com.jonathanhuertas.taskmanager2.domain.ProjectTask;
import com.jonathanhuertas.taskmanager2.exceptions.ProjectNotFoundException;
import com.jonathanhuertas.taskmanager2.repositories.BacklogRepository;
import com.jonathanhuertas.taskmanager2.repositories.ProjectRepository;
import com.jonathanhuertas.taskmanager2.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask){

        //PTs -> to be added to a specific project, so not null project -> means backlog exists
        Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);

        try{
            //set the backlog to the project task
            projectTask.setBacklog(backlog);

            //we want our project sequence to be  IDPRO-1 IDPRO-2 and we want sequence to keep growing aka if one is deleted we dont add it again
            Integer BacklogSequence = backlog.getPTSequence();

            //update the backlog sequence
            BacklogSequence++; //starts sequence right away

            backlog.setPTSequence(BacklogSequence);

            //Add sequence to project task
            projectTask.setProjectSequence(projectIdentifier+"-"+BacklogSequence);
            projectTask.setProjectIdentifier(projectIdentifier);
            //set an initial priority when priority is null
            if(projectTask.getPriority() == null){ //in future, need
                projectTask.setPriority(3);
            }

            //set an initial status when status is null
            if(projectTask.getStatus() == "" || projectTask.getStatus() == null){
                projectTask.setStatus("TO_DO");
            }

            return projectTaskRepository.save(projectTask);

        } catch(Exception e){
            throw new ProjectNotFoundException("Project not found");
        }

    }

    public Iterable<ProjectTask> findBacklogById(String id){

        Project project = projectRepository.findByProjectIdentifier(id);

        if(project == null) {
            throw new ProjectNotFoundException("Project with ID " + id + " does not exist");
        }

        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }
}
