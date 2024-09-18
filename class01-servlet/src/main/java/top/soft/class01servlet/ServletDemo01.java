package top.soft.class01servlet;


import jakarta.servlet.*;

import java.io.IOException;



public class ServletDemo01 implements Servlet {

    /**
     * Servlet 初始化
     * @param servletConfig
     * @throws ServletException
     */

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        System.out.println("ServletDemo01 初始化");

    }

    /**
     * 获取 Servlet 配置
     * @return
     */

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    /**
     * 执行 Service 方法
     * @param servletRequest
     * @param servletResponse
     * @throws ServletException
     * @throws IOException
     */

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        servletResponse.setCharacterEncoding("UTF-8");
        servletResponse.getWriter().write("servletDemo1 执行");
        System.out.println("service 执行");

    }

    /**
     * 获取 srevlet 信息
     * @return
     */

    @Override
    public String getServletInfo() {
        return "";
    }

    /**
     * 执行销毁方法
     */
    @Override
    public void destroy() {
        System.out.println("销毁方法");

    }
}