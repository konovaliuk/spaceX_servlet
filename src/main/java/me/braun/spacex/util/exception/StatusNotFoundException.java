package me.braun.spacex.util.exception;

public class StatusNotFoundException extends AbstractNotFoundException{
    public StatusNotFoundException(){
        super("Status not found");
    }
}
