package top.soft.class03response.response;


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
 * @author Administrator
 * @description: TODO
 * @date 2024/9/28 14:55
 */
@WebServlet("/res")
public class ResponseTypeDemo extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String type = req.getParameter("type");
        switch (type) {
            case "image":
                getImage(req, resp);
            case "pdf":
                getPdf(req, resp);
                break;
            case "text":
                getText(req,resp);
                break;

            default:
                break;
        }

    }

    private void getImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("image/jpg");
        String realPath = request.getServletContext().getRealPath("/images/1.jpg");
        File file = new File(realPath);
        InputStream in = new FileInputStream(file);
        int read = 0;
        ServletOutputStream out = response.getOutputStream();
        while ((read = in.read()) != -1) {
            out.write(read);
        }
        out.flush();
        out.close();
    }

    private void getPdf(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");

        String realPath = request.getServletContext().getRealPath("/pdf" +
                "/3.pdf");

        File file = new File(realPath);
        InputStream in = new FileInputStream(file);

        int read = 0;
        ServletOutputStream out = response.getOutputStream();
        while ((read = in.read()) != -1) {
            out.write(read);
        }
        out.flush();
        out.close();
    }
    private void getText(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/txt");
        String realPath = request.getServletContext().getRealPath("/text/2.txt");
        File file = new File(realPath);
        InputStream in = new FileInputStream(file);
        int read = 0;
        ServletOutputStream out = response.getOutputStream();
        while ((read = in.read()) != -1) {
            out.write(read);
        }
        out.flush();
        out.close();
    }


}