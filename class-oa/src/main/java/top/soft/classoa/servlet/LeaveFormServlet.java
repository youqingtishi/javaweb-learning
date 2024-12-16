package top.soft.classoa.servlet;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import top.soft.classoa.entity.LeaveForm;
import top.soft.classoa.service.LeaveFormService;
import top.soft.classoa.utils.ResponseUtils;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @description: TODO
 * @date 2024/12/12 20:58
 */
@WebServlet("/api/leave/*")
public class LeaveFormServlet extends HttpServlet {
    private final LeaveFormService leaveFormService = new LeaveFormService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("utf-8");
        //根据地址请求，截取最后的字符串，分发到不同的方法
        String uri = req.getRequestURI();
        String methodName = uri.substring(uri.lastIndexOf("/") + 1);
        switch(methodName){
            //创建请假单
            case "create" -> this.create(req,resp);
            //查询请假单
            case "list" -> this.list(req,resp);
            //审批
            case "audit" ->this.audit(req,resp);
            default -> System.out.println("请求错误");
        }
    }

    private void create(HttpServletRequest req,HttpServletResponse resp) throws ServletException , IOException{
        String employeeId = req.getParameter("eid");
        String formType = req.getParameter("formType");
        String startTime = req.getParameter("startTime");
        String endTime = req.getParameter("endTime");
        String reason = req.getParameter("reason");

        //生成请假单
        LeaveForm form = LeaveForm.builder()
                .employeeId(Long.parseLong(employeeId))
                .formType(Integer.parseInt(formType))
                .startTime(new Date(Long.parseLong(startTime)))
                .endTime(new Date(Long.parseLong(endTime)))
                .reason(reason)
                .createTime(new Date())
                .build();

        ResponseUtils result;
        try {
            LeaveForm leaveForm = leaveFormService.createLeaveForm(form);
            result = new ResponseUtils();
            result.put("leaveForm",leaveForm);
            /*System.out.println(leaveForm);
            System.out.println(result);*/
        }catch (Exception e){
            e.printStackTrace();
            result = new ResponseUtils(e.getClass().getSimpleName(),e.getMessage());
        }
        resp.getWriter().println(result.toJsonString());
    }

    private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String employeeId = req.getParameter("eid");
        ResponseUtils result;
        try{
            List<Map<String, Object>> formList = leaveFormService.getLeaveFormList("process", Long.parseLong(employeeId));
            result = new ResponseUtils().put("list",formList);
            /*System.out.println(formList);
            System.out.println(result);*/
        }catch (Exception e){
            e.printStackTrace();
            result = new ResponseUtils(e.getClass().getSimpleName(), e.getMessage());
        }
        //System.out.println(result);
        resp.getWriter().println(result.toJsonString());
    }
    private void audit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String employeeId = req.getParameter("formId");
        String result = req.getParameter("result");
        String reason = req.getParameter("reason");
        String eid = req.getParameter("eid");

        ResponseUtils response;
        try{
            leaveFormService.audit(Long.parseLong(employeeId),Long.parseLong(eid),result,reason);
           response = new ResponseUtils();
        }catch (Exception e){
            e.printStackTrace();
            response = new ResponseUtils(e.getClass().getSimpleName(),e.getMessage());
        }
        resp.getWriter().println(response.toJsonString());
    }

}

