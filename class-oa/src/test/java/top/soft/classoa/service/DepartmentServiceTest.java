package top.soft.classoa.service;

import org.junit.jupiter.api.Test;
import top.soft.classoa.entity.Department;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentServiceTest {
    private final DepartmentService departmentService = new DepartmentService();

    @Test
    void selectById() {
        Department department = departmentService.selectById(3L);
        System.out.println(department);
    }
}