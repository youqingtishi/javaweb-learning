package top.soft.classoa.service;


import top.soft.classoa.entity.Department;
import top.soft.classoa.mapper.DepartmentMapper;
import top.soft.classoa.utils.MyBatisUtils;

/**
 * @author Administrator
 * @description: TODO
 * @date 2024/12/7 14:58
 */
public class DepartmentService {

    public Department selectById(Long departmentId){
        return(Department) MyBatisUtils.executeQuery(sqlSession ->
                sqlSession.getMapper(DepartmentMapper.class).selectById(departmentId));
    }
}
