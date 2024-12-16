package top.soft.bookonline.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import top.soft.bookonline.entity.CaptchaGenerator;
import top.soft.bookonline.entity.User;
import top.soft.bookonline.service.UserService;
import top.soft.bookonline.service.impl.UserServiceImpl;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserService userService ;

    @Override
    public void init(ServletConfig config) throws ServletException {
        userService = new UserServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取表单数据
        String account = req.getParameter("account");
        String password = req.getParameter("password");
        String remember = req.getParameter("remember");
        String captcha = req.getParameter("captcha");

        // 验证验证码
        String sessionCaptcha = (String) req.getSession().getAttribute("captcha");
        if (captcha == null || !captcha.equalsIgnoreCase(sessionCaptcha)) {
            resp.setContentType("text/html;charset=utf-8");
            resp.setCharacterEncoding("utf-8");
            resp.getWriter().write("<script>alert('验证码错误');location.href='/';</script>");
            return;
        }

        // 调用登录功能
        User user = userService.signIn(account, password);
        if (user != null) {
            req.getSession().setAttribute("user", user);
            if (remember != null) {
                Cookie usernameCookie = new Cookie("username", account);
                Cookie passwordCookie = new Cookie("password", password);
                usernameCookie.setMaxAge(60 * 60 * 24 * 7);
                passwordCookie.setMaxAge(60 * 60 * 24 * 7);
                resp.addCookie(usernameCookie);
                resp.addCookie(passwordCookie);
            }
            // 登录成功，将用户信息存入session

            // 重定向到首页
            resp.sendRedirect("/index");
        } else {
            // 登录失败，跳转回登录页面
            resp.setContentType("text/html;charset=utf-8");
            resp.setCharacterEncoding("utf-8");
            resp.getWriter().write("<script>alert('登录失败,账号或密码错误');location.href='/';</script>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 生成验证码
        CaptchaGenerator captchaGenerator = new CaptchaGenerator();
        String captcha = captchaGenerator.generateCaptcha();

        // 将验证码存储在会话中
        req.getSession().setAttribute("captcha", captcha);

        // 将验证码以图像形式发送到客户端
        BufferedImage captchaImage = captchaGenerator.createImage(captcha);
        ServletOutputStream outputStream = resp.getOutputStream();
        ImageIO.write(captchaImage, "png", outputStream);
        outputStream.close();
    }
}
