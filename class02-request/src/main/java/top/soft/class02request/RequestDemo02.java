package top.soft.class02request;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.Enumeration;

@WebServlet("/requestDemo02")
public class RequestDemo02 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        获取请求数据
//        1、遍历所有请求数据
        Enumeration<String> headerNames = req.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
//            根据名称获取请求头的值
            String header = req.getHeader(name);
            System.out.println(name + ":" + header);
        }

//        2、根据名称获取请求头的值
        String header = req.getHeader("user-agent");
        if (header.contains("Edg")) {
            System.out.println("Edg浏览器");
        } else {
            System.out.println("Chrome浏览器");
        }
    }
}
