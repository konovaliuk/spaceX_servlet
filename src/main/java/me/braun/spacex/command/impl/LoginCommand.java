package me.braun.spacex.command.impl;

import lombok.AllArgsConstructor;
import me.braun.spacex.command.ICommand;
import me.braun.spacex.persistence.entity.Account;
import me.braun.spacex.service.LoginService;
import me.braun.spacex.util.AttributeNames;
import me.braun.spacex.util.Config;
import me.braun.spacex.util.RequestParameters;
import me.braun.spacex.util.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@AllArgsConstructor
public class LoginCommand implements ICommand {
    private final Logger log = LoggerFactory.getLogger(LoginCommand.class);
    private final LoginService login = new LoginService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        log.info("Start of sign in process");
        String email = request.getParameter(RequestParameters.EMAIL);
        String password = request.getParameter(RequestParameters.PASSWORD);

        try {
            Account account = login.signIn(email, password);
            request.getSession().setAttribute(AttributeNames.USER, account);
            log.info("User successfully signed in");
            return "redirect:" + Config.INDEX;
        } catch (ServiceException e) {
            log.info("User fail sign in", e);
            request.setAttribute("SignInError", e.getMessage());
            return Config.SIGNIN;
        }
    }
}
