package org.diallo.angular.entities;

public class DataIntegrityViolationException extends RuntimeException {
    public DataIntegrityViolationException(){
        super();
    }
    public DataIntegrityViolationException(String message){
        super(message);
    }
}
