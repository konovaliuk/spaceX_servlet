package me.braun.spacex.command.impl;

import me.braun.spacex.command.ICommand;
import me.braun.spacex.persistence.entity.Account;
import me.braun.spacex.service.AdminService;
import me.braun.spacex.util.AttributeNames;
import me.braun.spacex.util.Config;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetAccountsCommand implements ICommand {

    private final Logger log = LoggerFactory.getLogger(GetAccountsCommand.class);
    private final AdminService adminService = new AdminService();
    @Override
    public @Nullable String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int pageNum = 1;
        int limit = 4;
        int numOfUsers = adminService.getUsersAmount();
        if (request.getParameter("pageNum") != null)
            pageNum = Integer.parseInt(request.getParameter("pageNum"));
        int numOfPages = (int)Math.ceil(numOfUsers * 1.0 / limit);
        try {
            List<Account> users = adminService.getUsers(pageNum);
            request.setAttribute(AttributeNames.USERS, users);
            request.setAttribute(AttributeNames.PAGENUM, pageNum);
            request.setAttribute(AttributeNames.NUMOFPAGES, numOfPages);
            log.info(users.toString());
            return Config.USER;
        }
        catch (Exception e){
            request.setAttribute(AttributeNames.ERROR, e.getMessage());
            log.info(e.getMessage(), e);
            return Config.INDEX;
        }
    }

}
