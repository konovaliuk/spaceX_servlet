package me.braun.spacex.command.impl;

import me.braun.spacex.command.ICommand;
import me.braun.spacex.persistence.dao.DAOFactory;
import me.braun.spacex.persistence.dao.IServiceTypeDAO;
import me.braun.spacex.persistence.dao.ISpacecraftDAO;
import me.braun.spacex.persistence.entity.Account;
import me.braun.spacex.persistence.entity.Mission;
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
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class EditMissionCommand implements ICommand {
    private final Logger log = LoggerFactory.getLogger(EditMissionCommand.class);
    private final ISpacecraftDAO spacecraftDAO = DAOFactory.getSpaceCraftDAO();
    private final IServiceTypeDAO serviceTypeDAO = DAOFactory.getServiceTypeDAO();
    private final MissionService missionService = new MissionService();

    @Override
    public @Nullable String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException {
        Long id = Long.parseLong(request.getParameter(RequestParameters.MISSIONID));
        String title = request.getParameter(RequestParameters.TITLE);
        String description = request.getParameter(RequestParameters.ABOUT);
        Long customer = ((Account) request.getSession().getAttribute(AttributeNames.USER)).getId();

        List<String> spacecrafts = missionService.getSpacecraftTitles();
        request.setAttribute(AttributeNames.SPACECRAFT, spacecrafts);
        long spacecraft = Long.parseLong(request.getParameter(RequestParameters.SPACECRAFTID));

        List<String> serviceTypes = missionService.getServiceTypes();
        request.setAttribute(AttributeNames.SERVICE, serviceTypes);
        byte service = Byte.parseByte(request.getParameter(RequestParameters.SERVICEID));

        double payloadWeigh = Double.parseDouble(request.getParameter(RequestParameters.PAYLOAD));

        String startDateStr = request.getParameter(RequestParameters.DATESTART);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = new Date(sdf.parse(startDateStr).getTime());
        int duration = Integer.parseInt(request.getParameter(RequestParameters.DURATION));
//        String endDateStr = request.getParameter(RequestParameters.DATEEND);
//        Date endDate = (Date) sdf.parse(endDateStr);
//        int duration = (int) (endDate.getTime() - startDate.getTime()) / 86400000;

        try {
            Mission mission = missionService.editMission(id, title, description,
                    customer, spacecraft, service, payloadWeigh, startDate,  duration);
            request.setAttribute(AttributeNames.MISSION, mission);
            response.setStatus(200);
                return Config.GETUSERMISSIONS;
        } catch (Exception e) {
            request.setAttribute(AttributeNames.ERROR, e.getMessage());
            log.error(e.getMessage(), e);
            return Config.INDEX;
        }
    }
}
