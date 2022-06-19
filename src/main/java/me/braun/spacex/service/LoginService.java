package me.braun.spacex.service;

import me.braun.spacex.persistence.dao.DAOFactory;
import me.braun.spacex.persistence.entity.Account;
import me.braun.spacex.util.PasswordHashing;
import me.braun.spacex.util.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.Optional;

public class LoginService {
    private final Logger log = LoggerFactory.getLogger(LoginService.class);
    public Account signIn(String email, String password){
        log.info("Try to sign in");
        if (Objects.isNull(email) || Objects.isNull(password))
            throw new ServiceException("Please fill all of the fields of the form!");

        Optional<Account> user = DAOFactory.getAccountDAO().findByEmail(email);
        AccountService.checkEmailValid(email);
        if (user.isEmpty()){
            throw new ServiceException("Wrong credentials");
        }
        Account account = user.get();
        try {
            if (!PasswordHashing.validatePassword(password, account.getPasswordHash())){
                throw new ServiceException("Wrong password!");
            }
        } catch (Exception e){
            throw new ServiceException("Something went wrong", e);
        }
        return account;
    }
}
