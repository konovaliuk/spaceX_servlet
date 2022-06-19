package me.braun.spacex.command.filter;

import me.braun.spacex.command.CommandFactory;
import me.braun.spacex.persistence.entity.Account;
import me.braun.spacex.persistence.entity.enams.ERole;
import me.braun.spacex.util.AttributeNames;
import me.braun.spacex.util.Config;
import me.braun.spacex.util.RequestParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class AuthorizationFilter implements Filter {
    private static final Set<String> secureCommands = new HashSet<>();
    private final Logger log = LoggerFactory.getLogger(AuthorizationFilter.class);
    static {
        secureCommands.add(CommandFactory.SET_ROLE_COMMAND);
        secureCommands.add(CommandFactory.SET_STATUS_COMMAND);
        secureCommands.add(CommandFactory.GET_USERS_COMMAND);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String securePath = req.getContextPath() + "/admin";
        String command = req.getParameter(RequestParameters.COMMAND);
        boolean isSecurePageRequest = req.getRequestURI().startsWith(securePath);
        boolean isSecureCommandRequest = isSecureCommand(command);

        if (isSecurePageRequest || isSecureCommandRequest) {
            if (isUserAdmin(req)) {
                chain.doFilter(req, resp);

            } else {
                RequestDispatcher dispatcher = req.getRequestDispatcher(Config.ERROR);
                dispatcher.forward(req, resp);
            }
        } else {
            chain.doFilter(req, resp);
        }
    }

    private boolean isSecureCommand(String command) {
        if (Objects.isNull(command))
            return false;
        return secureCommands.contains(command);
    }

    private boolean isUserAdmin(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        boolean isLoggedIn = Objects.nonNull(session) && Objects.nonNull(session.getAttribute(AttributeNames.USER));
        if (isLoggedIn) {
            Account user = (Account) session.getAttribute(AttributeNames.USER);
            log.info(user.getRole().getRole());
            return user.getRole().getId() == ERole.ADMIN.getId();
        } else {
            return false;
        }
    }
}
