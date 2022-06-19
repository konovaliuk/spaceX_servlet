package me.braun.spacex.command.filter;

import me.braun.spacex.command.CommandFactory;
import me.braun.spacex.util.AttributeNames;
import me.braun.spacex.util.Config;
import me.braun.spacex.util.RequestParameters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;


public class AuthenticationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        String rootURI = req.getContextPath() + "/";
        String indexURI = req.getContextPath() + Config.INDEX;
        String registrationURI = req.getContextPath() + Config.REGISTER;
        String signInURI = req.getContextPath() + Config.SIGNIN;
        String command = req.getParameter(RequestParameters.COMMAND);

        boolean isLoggedIn = Objects.nonNull(session) && Objects.nonNull(session.getAttribute(AttributeNames.USER));
        boolean isRootRequest = req.getRequestURI().equals(rootURI);
        boolean isIndexRequest = req.getRequestURI().equals(indexURI);
        boolean isRegistrationRequest = req.getRequestURI().equals(registrationURI);
        boolean isSignInRequest = req.getRequestURI().equals(signInURI);
        boolean isSignInCommand = Objects.nonNull(command) && command.equals(CommandFactory.LOGIN_COMMAND)
                && req.getMethod().equals("POST");
        boolean isRegistrationCommand = Objects.nonNull(command) && command.equals(CommandFactory.REG_COMMAND)
                && req.getMethod().equals("POST");
        boolean isLocalizationCommand = Objects.nonNull(command) && command.equals(CommandFactory.LOCALIZATION_COMMAND);
        boolean isFile = req.getRequestURI().substring(req.getRequestURI().lastIndexOf('/')).contains(".");

        if (isLoggedIn) {
            if (isRegistrationRequest || isSignInRequest || isRegistrationCommand || isSignInCommand) {
                resp.sendRedirect(indexURI);
            } else {
                chain.doFilter(req, resp);
            }
        } else {
            if (isIndexRequest || isLocalizationCommand || isRegistrationRequest || isRootRequest ||
                    isSignInRequest || isRegistrationCommand || isSignInCommand || isFile ) {
                chain.doFilter(req, resp);
            } else {
                resp.sendRedirect(signInURI);
            }
        }
    }

    @Override
    public void destroy() {

    }
}
