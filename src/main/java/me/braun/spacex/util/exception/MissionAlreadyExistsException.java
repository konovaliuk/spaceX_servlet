package me.braun.spacex.util.exception;

public class MissionAlreadyExistsException extends RuntimeException{
    public MissionAlreadyExistsException(){
        super("Mission already exists!");
    }
    public MissionAlreadyExistsException(String title){
        super("Mission with the same email is already exist. Please choose another one.");
    }
}
