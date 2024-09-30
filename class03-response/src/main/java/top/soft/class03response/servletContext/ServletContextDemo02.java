package top.soft.class03response.servletContext;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author 刘文博
 * @description: TODO
 * @date 2024/9/28 16:43
 */
@WebServlet("/servletContextDemo02")
public class ServletContextDemo02 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ServletContext servletContext = req.getServletContext();
        resp.setContentType("text/html;charset=utf-8");

        String aReadPath = servletContext.getRealPath("/a.txt");
        System.out.println(aReadPath);

        String bRealPath = servletContext.getRealPath("/WEB-INF/b.txt");
        System.out.println(bRealPath);

        String cRealPath = servletContext.getRealPath("WEB-INF/classes/c.txt");
        System.out.println(cRealPath);

        File file=new File(bRealPath);
        InputStream in =new FileInputStream(file);
        int read=0;
        ServletOutputStream out=resp.getOutputStream();
        while ((read=in.read())!=-1){
            out.write(read);
        }
        in.close();
        out.flush();
        out.close();


    }
}
