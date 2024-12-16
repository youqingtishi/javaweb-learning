package top.soft.bookonline.service.impl;

/**
 * @author 刘文博
 * @description: TODO
 * @date 2024/11/6 13:35
 */

import top.soft.bookonline.dao.UserDao;
import top.soft.bookonline.dao.impl.UserDaoImpl;
import top.soft.bookonline.entity.User;
import top.soft.bookonline.service.UserService;

public class UserServiceImpl implements UserService {
    private final UserDao userDao = new UserDaoImpl();

    @Override
    public User
    signIn(String account, String password) {
        User user = User.builder().account(account).password(password).build();
        return userDao.findUser(user);
    }

    @Override
    public int
    register(String account, String password, String nickName) {
        User user = User.builder().account(account).password(password).nickname(nickName).build();
        return userDao.insertUser(user);
    }
}