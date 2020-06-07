package com.jonathanhuertas.taskmanager2.web;


import com.jonathanhuertas.taskmanager2.services.MapValidationErrorService;
import com.jonathanhuertas.taskmanager2.services.ProjectService;
import com.jonathanhuertas.taskmanager2.domain.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/project")
public class ProjectController {


    @Autowired
    private ProjectService projectService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;


    //@Valid checks to see if it is a valid object
    //BindingResult interface analyzes object that is labeled with @Valid and determines whether there are errors
    //BindingResult inherits methods from Errors
    @PostMapping("")
    public ResponseEntity<?> createNewPrpoject(@Valid @RequestBody Project project, BindingResult result){

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        //if there are errors on the errorMap, return the errors to the client
        if(errorMap != null){
            return errorMap;
        }


        projectService.saveOrUpdateProject(project); //this is where errors will occur for database exceptions when persisting
        return new ResponseEntity<Project>(project, HttpStatus.CREATED);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable String projectId){

        Project project = projectService.findProjectByIdentifier(projectId);

        return new ResponseEntity<Project>(project, HttpStatus.OK);
    }

    @GetMapping("/all")
    public Iterable<Project> getAllProjects(){
        return projectService.findAllProjects();
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable String projectId){
        projectService.deleteProjectByIdentifier(projectId);

        return new ResponseEntity<String>("Project with ID: " + projectId + " was deleted", HttpStatus.OK);
    }

}
