package top.soft.bookonline.service;

/**
 * @author 刘文博
 * @description: TODO
 * @date 2024/11/6 13:34
 */
import top.soft.bookonline.entity.User;

public interface UserService {
    /**
     * 用户登录功能
     *
     * @param account  账号
     * @param password 密码
     * @return user
     */
    User signIn(String account, String password);

    int
    register(String account, String password, String nickName);
}
