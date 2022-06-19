package me.braun.spacex.util.exception;


public class MissionNotFoundException extends AbstractNotFoundException{
    public MissionNotFoundException(){
        super("Mission not found");
    }
}
