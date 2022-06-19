package me.braun.spacex.util.exception;

public class AccountNotFoundException extends AbstractNotFoundException {
    public AccountNotFoundException(){
        super("User not found");
    }
}
