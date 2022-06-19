package me.braun.spacex.command.impl;

import me.braun.spacex.command.ICommand;
import me.braun.spacex.persistence.entity.Account;
import me.braun.spacex.persistence.entity.Mission;
import me.braun.spacex.service.MissionService;
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

public class GetUserMissionsCommand implements ICommand {
    private final Logger log = LoggerFactory.getLogger(GetUserMissionsCommand.class);
    private final MissionService missionService = new MissionService();
    @Override
    public @Nullable String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Account account = (Account) request.getSession().getAttribute(AttributeNames.USER);

        try {
            int pageNum = 1;
            int limit = 6;
            int numOfMissions = missionService.getUserMissionsAmount(account);
            if (request.getParameter("pageNum") != null)
                pageNum = Integer.parseInt(request.getParameter("pageNum"));
            int numOfPages = (int)Math.ceil(numOfMissions * 1.0 / limit);
            List<Mission> missions = missionService.getUserMissions(account, pageNum);
            request.setAttribute(AttributeNames.MISSIONS, missions);
            request.setAttribute(AttributeNames.PAGENUM, pageNum);
            request.setAttribute(AttributeNames.NUMOFPAGES, numOfPages);
            return Config.MISSIONS;
        }
        catch (Exception e){
            request.setAttribute(AttributeNames.ERROR, e.getMessage());
            response.setStatus(400);
            return Config.INDEX;
        }
    }
}
