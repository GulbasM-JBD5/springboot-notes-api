package com.example.thirdyear.exception;
import jakarta.servlet.http.HttpServletRequest;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.servlet.function.ServerResponse.badRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    // validasiya xetasi
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity <ErrorResponse> handleValidationException(MethodArgumentNotValidException ex,
                                                                    HttpServletRequest request){
        //<String> ,Map<String,String> idi evvel
       var errors= ex.getBindingResult().getFieldErrors();
        //  return ResponseEntity.badRequest().body("Validation failed");
       //  return ResponseEntity.badRequest().body(errors.get(0).getDefaultMessage());
        Map<String,String > errorsss =new HashMap<>();
        for(FieldError fe :errors){
            errorsss.put(fe.getField(), fe.getDefaultMessage());
            //Validasiya field errorudur bu getDefaulteeror yalniz filed error ucundur
        }
        // return ResponseEntity.badRequest().body(errorsss);
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(400);
        errorResponse.setError(errorsss);
        errorResponse.setMessage("Validation failed");
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setPath(request.getRequestURI());
        return ResponseEntity.badRequest().body(errorResponse);

    }
    //404 not found xetasi
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException ex,
                                                                       HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(ex.getStatusCode().value());
        errorResponse.setMessage(ex.getReason());
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setPath(request.getRequestURI());
        return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);

    }
    //umumi exception classi
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleExceptionError(Exception ex,
                                                              HttpServletRequest request){

        ErrorResponse  errorResponse=new ErrorResponse();
        errorResponse.setStatus(500);
        errorResponse.setMessage(" An unexpected error occurred.");
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setPath(request.getRequestURI());
       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
