package me.braun.spacex.command.impl;

import me.braun.spacex.command.ICommand;
import me.braun.spacex.persistence.entity.Account;
import me.braun.spacex.service.AccountService;
import me.braun.spacex.util.AttributeNames;
import me.braun.spacex.util.Config;
import me.braun.spacex.util.RequestParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterCommand implements ICommand {

    private final Logger log = LoggerFactory.getLogger(RegisterCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter(RequestParameters.EMAIL);
        try {

            Account account = AccountService.registration(
                    request.getParameter(RequestParameters.FIRSTNAME),
                    request.getParameter(RequestParameters.LASTNAME),
                    email,
                    request.getParameter(RequestParameters.PHONE),
                    request.getParameter(RequestParameters.PASSWORD)
            );

            request.getSession().setAttribute(AttributeNames.USER, account);
            response.setStatus(201);
            log.info("New user registered " + request.getParameter(RequestParameters.FIRSTNAME));
            return "redirect:" + Config.INDEX;
        } catch (Exception e) {
            request.setAttribute(AttributeNames.ERROR, e.getMessage());
            return Config.REGISTER;
        }
    }
}
