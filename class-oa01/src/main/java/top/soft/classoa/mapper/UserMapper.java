package top.soft.classoa.mapper;

import org.apache.ibatis.session.SqlSession;
import top.soft.classoa.entity.User;
import top.soft.classoa.utils.MybatisUtils;

public interface UserMapper {
    public User selectByUsername(String username){
        return (User) MybatisUtils.executeQuery(
                sqlSession -> sqlSession.selectOne(top.soft.classoa.mapper.UserMapper.)
        )
    }
}
