package top.soft.class05ajaxaxios;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author 刘文博
 * @description: TODO
 * @date 2024/11/9 14:27
 */
@WebServlet("/check")
public class CheckServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        1.从请求参数中获取用户名
        String username = req.getParameter("username");
//        2.判断用户名是否存在
        boolean equals = !"admin".equals(username);
        resp.getWriter().write(String.valueOf(equals));
    }
}
