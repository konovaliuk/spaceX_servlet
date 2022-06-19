package me.braun.spacex.util.exception;

public abstract class AbstractNotFoundException extends RuntimeException {
    protected AbstractNotFoundException(String message){
        super(message);
    }
}
