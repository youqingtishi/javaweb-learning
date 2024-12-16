package top.soft.classoa.mapper;
import top.soft.classoa.entity.User;
import top.soft.classoa.utils.MyBatisUtils;

public class UserMapper {
    public User selectByUsername(String username){
        return(User) MyBatisUtils.executeQuery(
                sqlSession -> sqlSession.selectOne("top.soft.classoa.mapper.UserMapper.selectByUsername",username)
        );
    }
}
