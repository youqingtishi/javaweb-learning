package top.soft.bookonline.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import top.soft.bookonline.dao.UserDao;
import top.soft.bookonline.dao.impl.UserDaoImpl;
import top.soft.bookonline.entity.User;
import top.soft.bookonline.service.UserService;
import top.soft.bookonline.service.impl.UserServiceImpl;

import java.io.IOException;


@WebServlet("/register")
public class RegServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        userService = new UserServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取表单数据
        String account = req.getParameter("account");
        String password = req.getParameter("password");
        String rePassword = req.getParameter("rePassword");
        String nickname = req.getParameter("nickname");

        //调用登录功能
        User user = userService.signIn(account, password);
        UserDao userDao = new UserDaoImpl();

        if (!password.equals(rePassword)) {
            resp.setContentType("text/html;charset=UTF-8");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write("<script>alert('两次密码不一致');location.href='/login-page'</script>");
            return;
        } else {
            if (user != null) {
                resp.setContentType("text/html;charset=UTF-8");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write("<script>alert('账号已存在');location.href='/login-page'</script>");
            } else {
                userDao.insertUser(User.builder().account(account).password(password).address("").avatar(nickname).build());
                System.out.println(account + ":" + password);
                resp.setContentType("text/html;charset=UTF-8");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write("<script>alert('注册成功，请登录！');location.href='/login-page'</script>");
            }

        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
