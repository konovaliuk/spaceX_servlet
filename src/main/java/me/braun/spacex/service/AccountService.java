package me.braun.spacex.service;

import lombok.experimental.UtilityClass;
import me.braun.spacex.persistence.dao.DAOFactory;
import me.braun.spacex.persistence.dao.IAccountDAO;
import me.braun.spacex.persistence.dao.IRoleDAO;
import me.braun.spacex.persistence.entity.Account;
import me.braun.spacex.persistence.entity.enams.ERole;
import me.braun.spacex.util.PasswordHashing;
import me.braun.spacex.util.exception.AccountAlreadyExistsException;
import me.braun.spacex.util.exception.AccountNotFoundException;
import me.braun.spacex.util.exception.ServiceException;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.regex.Pattern;

@UtilityClass

public class AccountService {
    private final IAccountDAO accountDAO = DAOFactory.getAccountDAO();
    private final IRoleDAO roleDAO = DAOFactory.getRoleDAO();
    private final Logger log = LoggerFactory.getLogger(AccountService.class);

    private static final String emailRegex = "^(?=.{1,64}@)[A-Za-z0-9\\+_-]+(\\.[A-Za-z0-9\\+_-]+)*@"
            + "[^-][A-Za-z0-9\\+-]+(\\.[A-Za-z0-9\\+-]+)*(\\.[A-Za-z]{2,})$";
    private static final String phoneRegex = "^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$";

    public static void checkEmailValid(String email) throws ServiceException {
        if (!Pattern.compile(emailRegex).matcher(email).matches()) {
            throw new ServiceException("Email address isn't valid. Must contain @ and point");
        }
    }
    public static void checkPhoneValid(String phone) throws ServiceException {
        if (!Pattern.compile(phoneRegex).matcher(phone).matches()) {
            throw new ServiceException("Phone number isn't valid. Must contain + and <= 13 numbers");
        }
    }

    private static @NotNull String toNameCase(@NotNull String name){
        return name.toUpperCase().charAt(0) + name.toLowerCase().substring(1);
    }
    @NotNull
    public Account registration(String firstName, String lastName, String email, String phone, String password) {

        checkEmailValid(email);
        checkPhoneValid(phone);
        PasswordHashing.checkPasswordValid(password);
        accountDAO.findByEmail(email).ifPresent(account -> {
            throw new AccountAlreadyExistsException(email);
        });
        String passwordHash = null;
        try {
            passwordHash = PasswordHashing.createHash(password);
            //log.info("Password length after hashing: " + passwordHash.length());

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        Optional<Account> userPhone = accountDAO.findByPhone(phone);
        if (userPhone.isPresent())
            throw new ServiceException("User with the same phone already exists");
        return accountDAO.save(Account.builder()
                .firstName(toNameCase(firstName))
                .lastName(toNameCase(lastName))
                .email(email.toLowerCase())
                .phone(phone)
                .role(roleDAO.findById(ERole.CUSTOMER.getId()).get())
                .passwordHash(passwordHash).build());
    }

    public Account editAccount(Long userId, String firstName, String lastName, String email, String phone) {
        checkEmailValid(email);
        checkPhoneValid(phone);
        Account account = accountDAO.findById(userId).orElseThrow(AccountNotFoundException::new);
        account.setFirstName(firstName);
        account.setLastName(lastName);
        account.setEmail(email);
        account.setPhone(phone);
        accountDAO.update(account);
        return account;
    }

    public Account changePassword(Long userId, String password) {
        Account account = accountDAO.findById(userId).orElseThrow(AccountNotFoundException::new);
        try {
            PasswordHashing.checkPasswordValid(password);
            password = PasswordHashing.createHash(password);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        account.setPasswordHash(password);
        accountDAO.update(account);
        return account;
    }

    public void deleteAccount(Long id, String password) {
        Optional<Account> user = DAOFactory.getAccountDAO().findById(id);
        Account account = user.get();
        try {
            if (!PasswordHashing.validatePassword(password, account.getPasswordHash())){
                throw new ServiceException("Wrong password!");
            }
        } catch (Exception e){
            throw new ServiceException("Something went wrong", e);
        }
        accountDAO.delete(id);
    }


}
