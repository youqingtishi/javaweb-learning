package top.soft.class03response.pratice;


import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author Administrator
 * @description: TODO
 * @date 2024/9/28 22:17
 */
@WebServlet("/download")
public class DownloadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        获取请求参数，文件名称
        String filename = request.getParameter("filename");

//        找到文件服务器路径
        ServletContext servletContext = this.getServletContext();
        String realPath =servletContext.getRealPath("/images/"+filename);



//        用字节流关联
        FileInputStream fis = new FileInputStream(realPath);

//        获取文件的mime类型
        String mimeType = servletContext.getMimeType(filename);

//        设置响应头类型：content-type
        response.setHeader("content-type",mimeType);

//        设置响应头打开方式：contentdisposition
        response.setHeader("content-disposition","attachment; filename= "+filename);

//        将输入流的数据写道输出流中
        ServletOutputStream sos = response.getOutputStream();
        byte[] buff = new byte[1024*8];
        int len;
        while ((len = fis.read(buff)) !=-1) {
            sos.write(buff,0,len);
        }
        fis.close();
    }
}