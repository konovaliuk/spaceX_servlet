package me.braun.spacex.command.impl;

import me.braun.spacex.command.ICommand;
import me.braun.spacex.persistence.entity.Mission;
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

public class GetMissionCommand implements ICommand {
    private final Logger log = LoggerFactory.getLogger(GetMissionCommand.class);
    private final AdminService adminService = new AdminService();

    @Override
    public @Nullable String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter(RequestParameters.TITLE);
        Mission mission = adminService.getMissionByTitle(title);
        request.setAttribute(AttributeNames.MISSION, mission);
        return Config.MISSION;
    }
}
