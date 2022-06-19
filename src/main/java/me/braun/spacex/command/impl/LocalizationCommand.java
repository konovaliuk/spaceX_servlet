package me.braun.spacex.command.impl;

import me.braun.spacex.command.ICommand;
import me.braun.spacex.util.RequestParameters;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;
import java.util.Locale;
import java.util.Objects;

public class LocalizationCommand implements ICommand {
    private final Logger log = LoggerFactory.getLogger(LocalizationCommand.class);

    @Override
    public @Nullable String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("Try to change a language");
        String lang = request.getParameter(RequestParameters.LANG);
        if (Objects.nonNull(lang))
            Config.set(request.getSession(), Config.FMT_LOCALE, Locale.forLanguageTag(lang));
        log.info("A language was successfully changed");
        return me.braun.spacex.util.Config.INDEX;
    }
}
