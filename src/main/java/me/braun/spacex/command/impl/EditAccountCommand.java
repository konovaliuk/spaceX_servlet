package me.braun.spacex.command.impl;

import me.braun.spacex.command.ICommand;
import me.braun.spacex.persistence.entity.Account;
import me.braun.spacex.service.AccountService;
import me.braun.spacex.util.AttributeNames;
import me.braun.spacex.util.Config;
import me.braun.spacex.util.RequestParameters;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditAccountCommand implements ICommand {
    private final Logger log = LoggerFactory.getLogger(EditAccountCommand.class);
    @Override
    public @Nullable String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Long id = ((Account) request.getSession().getAttribute(AttributeNames.USER)).getId();
            Account account = AccountService.editAccount(id,
                    request.getParameter(RequestParameters.FIRSTNAME),
                    request.getParameter(RequestParameters.LASTNAME),
                    request.getParameter(RequestParameters.EMAIL),
                    request.getParameter(RequestParameters.PHONE)
            );

            request.getSession().setAttribute(AttributeNames.USER, account);
            response.setStatus(200);
            return Config.USER;
        }
        catch (Exception e){
            request.setAttribute(AttributeNames.ERROR, e.getMessage());
            response.setStatus(400);
            return Config.USER;
        }
    }
}
