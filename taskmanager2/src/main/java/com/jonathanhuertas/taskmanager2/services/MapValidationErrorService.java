package com.jonathanhuertas.taskmanager2.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;
/*
This service makes sure that the client is passing a valid Object when the @Valid is used.
This does not check for errors in regards to database persistence
so if we try to fetch a projectId that does not exist, this would not handle that error
 */
@Service
public class MapValidationErrorService {

    public ResponseEntity<?> MapValidationService(BindingResult result){
        //Object FieldError - has getters for getField and getDefaultMessage
        if(result.hasErrors()){
            //to get a key value pair for field errors and message
            Map<String, String> errorMap = new HashMap<>();

            for(FieldError error : result.getFieldErrors() ){
                //getting the field and the default message off the binding result
                //name of the field with error is the key, default message is the value
                //this returns and array of objects each with a fieldName and the error message from the validation that failed
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<Map<String, String>>(errorMap, HttpStatus.BAD_REQUEST);
        }

        return null;
    }
}
