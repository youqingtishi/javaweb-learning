package top.soft.brandlist.servlet;


import com.alibaba.fastjson.JSON;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import top.soft.brandlist.entity.Brand;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @author Administrator
 * @description: TODO
 * @date 2024/11/9 16:13
 */
@WebServlet("/brand")
public class BrandServlet extends HttpServlet {

    private List<Brand> getBrandList() {
        return List.of(
                Brand.builder().id(101).companyName("apple").brandName("iPhone16").description("苹果-iPhone16").ordered(1).build(),
                Brand.builder().id(102).companyName("huawei").brandName("mate60").description("华为-mate60").ordered(2).build(),
                Brand.builder().id(103).companyName("benz").brandName("mercedes").description("奔驰-梅赛德斯").ordered(3).build());
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json;charset=UTF-8");
        ServletContext context = req.getServletContext();
        Object brands = context.getAttribute("brands");

        List<Brand> brandList =null;

        if(brands instanceof List){
            brandList = (List<Brand>) brands;
        }
        brandList=brandList==null?getBrandList():brandList;
        context.setAttribute("brands",brandList);

        req.getServletContext().setAttribute("brands", brandList);
        String jsonString = JSON.toJSONString(brandList);
        resp.getWriter().write(jsonString);
    }
}
