package me.braun.spacex.command.impl;

import me.braun.spacex.command.ICommand;
import me.braun.spacex.util.Config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogOutCommand implements ICommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        return "redirect:" + Config.INDEX;
    }
}
