package top.soft.classoa.service;


import top.soft.classoa.entity.Employee;
import top.soft.classoa.mapper.EmployeeMapper;
import top.soft.classoa.utils.MyBatisUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @description: TODO
 * @date 2024/12/7 14:33
 */
public class EmployeeService {
    public Employee selectById(Long employeeId){
        return(Employee) MyBatisUtils.executeQuery(sqlSession -> {
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            return mapper.selectById(employeeId);
        });
    }
    public Employee selectLeader(Long employeeId) {
        return (Employee) MyBatisUtils.executeQuery(sqlSession -> {
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            Employee employee = mapper.selectById(employeeId);
            Map<String, Object> params = new HashMap<>();
            Employee leader = null;
            // 普通员工的领导、部门经理
            if (employee.getLevel() < 7) {
                params.put("level", 7);
                params.put("departmentId", employee.getDepartmentId());
                List<Employee> employees = mapper.selectByParams(params);
                leader = employees.get(0);
            } else if (employee.getLevel() == 7) {
                // 部门经理的领导：总经理
                params.put("level", 8);
                List<Employee> employees = mapper.selectByParams(params);
                leader = employees.get(0);
            } else if (employee.getLevel() == 8) {
                // 总经理，领导就是自己
                leader = employee;
            }
            return leader;
        });
    }
}
