package top.soft.bookonline.dao.impl;

/**
 * @author 刘文博
 * @description: TODO
 * @date 2024/11/6 13:32
 */
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import top.soft.bookonline.dao.UserDao;
import top.soft.bookonline.entity.User;
import top.soft.bookonline.util.JdbcUtil;
import top.soft.bookonline.util.Md5Util;

public class UserDaoImpl implements UserDao {
    private final JdbcTemplate jdbcTemplate = new JdbcTemplate(JdbcUtil.getDataSource());

    public int insertUser(User user) {
        String sql = "insert into t_user(account,password,nickname,avatar,address) values(?,?,?,?,?)";
        return jdbcTemplate.update(sql,user.getAccount(), Md5Util.crypt(user.getPassword()),user.getNickname(),user.getAvatar(),user.getAddress());
    }

    public User findUser(User user){
        try {
            String sql = "select * from t_user where account =? and password =?";
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), user.getAccount(), Md5Util.crypt(user.getPassword()));
        }catch(DataAccessException e){
            e.printStackTrace();
            return null;
        }

    }
}