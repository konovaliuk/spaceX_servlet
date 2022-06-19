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
import java.text.ParseException;

public class DeleteProfileCommand implements ICommand {
    private final Logger log = LoggerFactory.getLogger(DeleteProfileCommand.class);
    @Override
    public @Nullable String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException {
        Long id = ((Account) request.getSession().getAttribute(AttributeNames.USER)).getId();
        String password = request.getParameter(RequestParameters.PASSWORD);

        try {
            AccountService.deleteAccount(id, password);
            request.getSession().invalidate();
            log.info("account deleted");
            return "redirect:" + Config.INDEX;
        }
        catch (Exception e){
            request.setAttribute(AttributeNames.ERROR, e.getMessage());
            log.info(e.getMessage(), e);
            response.setStatus(400);
            return Config.USER;
        }
    }
}
