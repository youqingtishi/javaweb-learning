package top.soft.brandlist.servlet;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import top.soft.brandlist.entity.Brand;

import java.io.IOException;
import java.util.List;

@WebServlet("/deleteBrand")
public class DeleteBrandServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        if (idStr != null && !idStr.isEmpty()) {
            try {
                int id = Integer.parseInt(idStr);
                ServletContext servletContext = req.getServletContext();
                List<Brand> brands = (List<Brand>) servletContext.getAttribute("brands");
                if (brands != null) {
                    brands.removeIf(brand -> brand.getId() == id);
                    servletContext.setAttribute("brands", brands);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        resp.sendRedirect("http://localhost:8080/brand");
    }
}