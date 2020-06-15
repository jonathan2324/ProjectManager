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
            if( projectTask.getPriority() == 0 || projectTask.getPriority() == null){ //in future, need
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

        //returns an array of project tasks ordered by priority to client
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }

    public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id){

        //make sure we are searching on the right backlog -> even though we call it backlog_id, it is really the projectIdentifier
        Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);
        if(backlog == null){
            throw new ProjectNotFoundException("Project with ID " + backlog_id + " does not exist");

        }

        //make sure that our task exists
        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);
        //make sure the backlog/project id in the path corresponds to the right project
        if(projectTask == null){
            throw new ProjectNotFoundException("Project task " + pt_id + " not found");
        }

        //make sure the backlog/projectIdentifier in the path corresponds to the right project
        if(!projectTask.getProjectIdentifier().equals(backlog_id)){
            throw new ProjectNotFoundException("Project Task " + pt_id + " does not exist in project: " + backlog_id);
        }


        return projectTask;
    }

    //Update project task
    public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id){
        //find existing project task
        ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id);


        //replace if with updated task
        projectTask = updatedTask;

        //return update
        return projectTaskRepository.save(projectTask);
    }


    public void deletePTByProjectSequence(String backlog_id, String pt_id){
        ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id);

        projectTaskRepository.delete(projectTask);

    }








}
