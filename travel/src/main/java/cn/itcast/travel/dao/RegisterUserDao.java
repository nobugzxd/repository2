package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface RegisterUserDao {
    /**
     * 查询用户名是否存在
     *
     * @param username
     * @return
     */
    User findByUsername(String username);
/**
 * 存储新用户信息到数据库
 *
 * @param user
 */

    void setUser(User user);

    /**
     * 使用激活码查找用户信息
     * @param code
     * @return
     */
    User findUuid(String code);

    /**
     * 激活账户
     * @param user
     */
    public void activeUser(User user);

    /**
     * 登录验证
     * @param user
     * @return
     */
    User finLoginUser(User user);
}
