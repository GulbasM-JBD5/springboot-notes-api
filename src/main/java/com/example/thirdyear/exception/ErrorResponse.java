package com.example.thirdyear.exception;

import java.time.LocalDateTime;
import java.util.Map;

public class ErrorResponse {
    private  int status;
    private Map<String, String> errors;
    //new field
    private  String message;

    public int getStatus() {
        return status;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
    //timestamp ve path elave edek
    private String path;
    private LocalDateTime timestamp;

    public String getMessage() {
        return message;
    }
    public String getPath(){
        return path;
    }
    public LocalDateTime getTimestamp(){
        return timestamp;
    }



    public void setStatus(int status) {
        this.status = status;
    }

    public void setError(Map<String, String> errors) {
        this.errors = errors;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
 // bu obyekt status var birde errors  bu errors da bir map i saxlyir duzdu
// throw atdigim obyektde de  var 404,not field