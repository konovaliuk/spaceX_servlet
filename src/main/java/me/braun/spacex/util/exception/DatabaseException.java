package me.braun.spacex.util.exception;

public class DatabaseException extends RuntimeException{
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
