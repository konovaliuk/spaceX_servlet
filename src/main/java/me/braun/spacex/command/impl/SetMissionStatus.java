package me.braun.spacex.command.impl;

import me.braun.spacex.command.ICommand;
import me.braun.spacex.persistence.entity.Account;
import me.braun.spacex.persistence.entity.Mission;
import me.braun.spacex.service.AdminService;
import me.braun.spacex.service.MissionService;
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

public class SetMissionStatus implements ICommand {
    public final AdminService adminService = new AdminService();
    private final Logger log = LoggerFactory.getLogger(SetMissionStatus.class);
    @Override
    public @Nullable String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException {
        short statusId = Short.parseShort(request.getParameter(RequestParameters.STATUSID));
        long missionId = Long.parseLong(request.getParameter(RequestParameters.MISSIONID));
        long spacecraftId = Long.parseLong(request.getParameter(RequestParameters.SPACECRAFTID));

        try {
            Mission mission = adminService.editMissionStatus(missionId, spacecraftId, statusId);
            request.setAttribute(AttributeNames.MISSION, mission);
            response.setStatus(200);
            return  Config.GETMISSIONS;

        }
        catch (Exception e){
            request.setAttribute(AttributeNames.ERROR, e.getMessage());
            log.error(e.getMessage(), e);
            return Config.INDEX;
        }
    }
}
