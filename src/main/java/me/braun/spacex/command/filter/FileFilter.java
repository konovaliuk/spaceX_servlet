package me.braun.spacex.command.filter;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class FileFilter extends HttpFilter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        String path = request.getRequestURI().substring(request.getContextPath().length());
        String lastPathPart = path.substring(path.lastIndexOf('/'));
        if(lastPathPart.contains(".")){
            chain.doFilter(req, res);
        }
        else{
            if(!path.contains("/api"))
                request.getRequestDispatcher("/api" + path).forward(req, res);
            else
                request.getRequestDispatcher(path).forward(req, res);
        }
    }
}
