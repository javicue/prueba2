package com.javiersan.prueba.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.javiersan.prueba.exceptions.NaveNotFoundException;


@RestControllerAdvice
public class HandlerExceptionController {
   
    
    @ExceptionHandler(NoHandlerFoundException.class)  
    public ResponseEntity<Error> notFoundEx(NoHandlerFoundException e){  
                                                                          
        Error error = new Error();   
        error.setDate(new Date());  
        error.setError("Api rest no encontrado");
        error.setMessage(e.getMessage());
        error.setStatus(HttpStatus.NOT_FOUND.value());

        return ResponseEntity.status(404).body(error);  


    }   
    
    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String,String> numberFormatException(Exception ex){

         Map<String, String> error = new HashMap<>();
         error.put("date", new Date().toString()); 
         error.put("error", "número inválido o incorrecto, no tiene formato de dígito");
         error.put("message", ex.getMessage());
         error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value()+"");  

         return error;

      }

    
    @ExceptionHandler({NullPointerException.class,
                      HttpMessageNotWritableException.class,
                      NaveNotFoundException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String,String> naveNotFoundException(Exception ex){

         Map<String, String> error = new HashMap<>();
         error.put("date", new Date().toString()); 
         error.put("error", "la nave no existe");
         error.put("message", ex.getMessage());
         error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value()+""); 

         return error;

    }


    @ExceptionHandler({DataAccessException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String,String> dataAccessException(Exception ex){

         Map<String, String> error = new HashMap<>();
         error.put("date", new Date().toString()); 
         error.put("error", "Error al realizar el insert a la base de datos");
         error.put("message", ex.getMessage());
         error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value()+""); 

        return error;

    }
    
}
