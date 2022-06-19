package me.braun.spacex.util.exception;


public class RoleNotFoundException extends AbstractNotFoundException {
    public RoleNotFoundException(){
        super("Role not found");
    }
}
