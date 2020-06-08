package com.jonathanhuertas.taskmanager2.web;


import com.jonathanhuertas.taskmanager2.services.MapValidationErrorService;
import com.jonathanhuertas.taskmanager2.services.ProjectService;
import com.jonathanhuertas.taskmanager2.domain.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {


    @Autowired//gives us access to the JPA CRUD and custom methods to query, also provides access to Exception handling logic
    private ProjectService projectService;

    @Autowired//gives access to how we render the the validation errors
    private MapValidationErrorService mapValidationErrorService;


    //@Valid checks to see if it is a valid object with the fields annotations
    //BindingResult interface analyzes object that is labeled with @Valid and determines whether there are errors for those fields
    //BindingResult inherits methods from Errors
    @PostMapping("")
    public ResponseEntity<?> createNewPrpoject(@Valid @RequestBody Project project, BindingResult result){

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        //if there are errors on the errorMap, return the errors to the client
        if(errorMap != null){
            return errorMap; //this will return the Map of key, value errors and messages to the client
        }


        projectService.saveOrUpdateProject(project); //this is where errors will occur for database exceptions when persisting -> @Column errors
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
