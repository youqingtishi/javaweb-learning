package top.soft.classoa.servlet;


import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import top.soft.classoa.entity.User;
import top.soft.classoa.service.UserService;
import top.soft.classoa.utils.ResponseUtils;

import javax.security.auth.login.LoginException;
import java.io.IOException;

/**
 * @author Administrator
 * @description: TODO
 * @date 2024/11/30 15:51
 */
@WebServlet("/api/login")
public class LoginServlet extends HttpServlet {
    private UserService userService;
    @Override
    public void init(ServletConfig config) {
        userService = new UserService();
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=UTF-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        ResponseUtils result;

        User user = null;
        try {
            user = userService.login(username, password);
            user.setPassword(null);
            user.setSalt(null);
            result = new ResponseUtils().put("user", user);
        } catch (LoginException e) {
            e.printStackTrace();
            result = new ResponseUtils(e.getMessage().getClass().getSimpleName(),e.getMessage());
        }

        resp.getWriter().write(result.toJsonString());
    }
}