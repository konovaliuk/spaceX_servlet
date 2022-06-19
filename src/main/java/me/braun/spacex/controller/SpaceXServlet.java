package me.braun.spacex.controller;

import lombok.SneakyThrows;
import me.braun.spacex.command.CommandFactory;
import me.braun.spacex.command.ICommand;
import me.braun.spacex.util.RequestParameters;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.logging.Logger;

//@WebServlet(name = "SpaceXServlet", urlPatterns = "/api/*")
public class SpaceXServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(SpaceXServlet.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {

        boolean isFile = request.getRequestURI().substring(request.getRequestURI().lastIndexOf('/')).contains(".");

        if (!isFile) {
            int index = request.getRequestURI().lastIndexOf("api/") + 4;
            String commandURI = request.getRequestURI().substring(index);
            log.info("Command is: " + commandURI);
//            ICommand command = CommandFactory.getCommand(request.getParameter(RequestParameters.COMMAND));
            ICommand command = CommandFactory.getCommand(commandURI);
            String page = command.execute(request, response);

            if (page != null) {
                if (page.startsWith("redirect:")) {
                    response.sendRedirect(request.getContextPath() + page.substring(9));
                } else {
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
                    dispatcher.forward(request, response);
                }
            }

        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        try {
            processRequest(request, response);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


}
