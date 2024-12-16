package top.soft.brandlist.servlet;


import com.alibaba.fastjson.JSON;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import top.soft.brandlist.entity.Brand;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Hazel
 * @description: TODO
 * @date 2024/11/9 16:13
 */
@WebServlet("/addBrand")
public class AddBrandServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //使用缓冲字符流来读取请求体数据
        StringBuilder sb = new StringBuilder();
        String line;
        BufferedReader br =req.getReader();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }

        //json字符串转换为JAVA对象
        List<Brand>brands =new ArrayList<>((List<Brand>)req.getServletContext().getAttribute("brands"));
        Brand brand = JSON.parseObject(sb.toString(), Brand.class);
        brands.add(brand);
        req.getServletContext().setAttribute("brands",brands);
    }
}