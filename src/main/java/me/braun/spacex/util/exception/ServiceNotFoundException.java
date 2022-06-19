package me.braun.spacex.util.exception;


public class ServiceNotFoundException extends AbstractNotFoundException{
    public ServiceNotFoundException(){
        super("Service not found");
    }
}
