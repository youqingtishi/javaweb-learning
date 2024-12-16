package top.soft.bookonline.filter;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter( "/*")
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletResponse.setContentType("text/html;charset=utf-8");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String[] urls = {"/images", "/css", "login.html", "login-page", "/login"};
        String requestUrl = request.getRequestURI().toString();
        for (String url : urls) {
            if (requestUrl.contains(url)) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }
        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");
        if (user == null) {
            request.getRequestDispatcher("/login.html").forward(request, servletResponse);
        }else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}