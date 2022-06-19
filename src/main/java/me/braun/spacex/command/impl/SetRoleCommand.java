package me.braun.spacex.command.impl;

import me.braun.spacex.command.ICommand;
import me.braun.spacex.persistence.entity.Account;
import me.braun.spacex.service.AdminService;
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

public class SetRoleCommand implements ICommand {
    private final AdminService adminService = new AdminService();
    private final Logger log = LoggerFactory.getLogger(SetRoleCommand.class);
    @Override
    public @Nullable String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {

        String email = request.getParameter(RequestParameters.EMAIL);
        byte roleId = Byte.parseByte(request.getParameter(RequestParameters.ROLE));
        try {
            adminService.getUserByEmail(email);
            Account account = adminService.editUserRole(email, roleId);
            request.setAttribute(AttributeNames.USER, account);
            response.setStatus(200);
            return  Config.USERS;
        }
        catch (Exception e){
            request.setAttribute(AttributeNames.ERROR, e.getMessage());
            log.info(e.getMessage(),e);
            return Config.INDEX;
        }
    }
}
