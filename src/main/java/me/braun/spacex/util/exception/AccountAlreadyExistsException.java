package me.braun.spacex.util.exception;

public class AccountAlreadyExistsException extends RuntimeException{
    public AccountAlreadyExistsException(){
        super("User already exists!");
    }
    public AccountAlreadyExistsException(String email){
        super("User with the same email is already exist. Please choose another one.");
    }

}
