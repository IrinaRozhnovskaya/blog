package com.github.sigma.blog.routes;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebFilter(urlPatterns = "/*")
public class SpaRoutingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!(request instanceof HttpServletRequest)) {
            return;
        }
        HttpServletRequest req = (HttpServletRequest) request;

        if (isStruts(req)) {
            chain.doFilter(request, response);
        } else { // forward every other request to index page
            final String contextPath = ((HttpServletRequest) request).getContextPath();
            req.getRequestDispatcher(contextPath + "/index.html").forward(request, response);
        }
    }

    private boolean isStruts(HttpServletRequest req) {
        List<String> allowed = Arrays.asList("/api");
        for (String path : allowed) {
            if (req.getServletPath().startsWith(path)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void destroy() {

    }
}
