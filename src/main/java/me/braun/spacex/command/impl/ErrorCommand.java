package me.braun.spacex.command.impl;

import me.braun.spacex.command.ICommand;
import me.braun.spacex.persistence.dto.ErrorDTO;
import me.braun.spacex.util.exception.AbstractNotFoundException;
import me.braun.spacex.util.exception.AccountAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class ErrorCommand implements ICommand {

    private final Logger log = LoggerFactory.getLogger(ErrorCommand.class);
    private final Throwable th;

    public ErrorCommand(Throwable th) {
        this.th = th;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (th instanceof AbstractNotFoundException) {
            response.setStatus(404);
            response.setContentType("application/json");
            response.getWriter().write(new ErrorDTO(404, th.getMessage()).toJson());
        } else if (th instanceof AccountAlreadyExistsException) {
            response.setStatus(400);
            response.setContentType("application/json");
            response.getWriter().write(new ErrorDTO(400, th.getMessage()).toJson());
        } else {
            response.setStatus(500);
            response.setContentType("application/json");
            response.getWriter().write(new ErrorDTO(500, th.getMessage()).toJson());
            log.error(th.getMessage(), th);
        }
        return null;
    }
}
