package me.braun.spacex.util.exception;

public class SpacecraftNotFoundException extends AbstractNotFoundException{
    public SpacecraftNotFoundException(){
        super("404 Spacecraft not found");
    }
}
