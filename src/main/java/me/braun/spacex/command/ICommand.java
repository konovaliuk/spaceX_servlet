package me.braun.spacex.command;

import org.jetbrains.annotations.Nullable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

public interface ICommand {
    @Nullable
    String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException;
}
