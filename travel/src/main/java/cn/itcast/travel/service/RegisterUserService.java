package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

public interface RegisterUserService {
    /**
     * 查询账户名是否存在并创建用户信息返回结果
     * @param user
     * @return boolean
     */
    boolean createUser(User user);

    /**
     * 激活账户
     * @param code
     * @return
     */
    boolean findUuid(String code);

    /**
     * 账户名密码校验登录
     * @param user
     * @return
     */
    User loginUser(User user);


}
