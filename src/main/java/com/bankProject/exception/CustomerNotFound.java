package com.bankProject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
 
@ResponseStatus(HttpStatus.NOT_FOUND)
public class CustomerNotFound extends RuntimeException {
 
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
 
    public CustomerNotFound(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }
 
}

