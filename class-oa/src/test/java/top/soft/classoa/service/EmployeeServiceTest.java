package top.soft.classoa.service;

import org.junit.jupiter.api.Test;
import top.soft.classoa.entity.Employee;
import top.soft.classoa.service.EmployeeService;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceTest {
    private final EmployeeService employeeService = new EmployeeService();
    @Test
    void selectById(){
        Employee employee = employeeService.selectById(1L);
        System.out.println(employee);
    }

}