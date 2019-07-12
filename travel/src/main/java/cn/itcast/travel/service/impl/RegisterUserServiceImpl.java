package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.RegisterUserDao;
import cn.itcast.travel.dao.impl.RegisterUserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.RegisterUserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

import java.util.UUID;

public class RegisterUserServiceImpl implements RegisterUserService {
    //获取dao对象
      RegisterUserDao dao = new RegisterUserDaoImpl();
    /**
     * 查询账户名是否存在并创建用户信息返回结果
     * @param user
     * @return boolean
     */
    @Override
    public boolean createUser(User user) {

            //1.调用dao方法查询该用户名是否存在
            User user1 = dao.findByUsername(user.getUsername());

            if (user1==null){
                //存储激活码和发送邮件
                String uuid = UuidUtil.getUuid();
                user.setCode(uuid);
                user.setStatus("N");
                //激活邮件正文发送，正文：
                String content = "<a href='/user/active?code="+uuid+"'>点击激活[白马旅游网]</a>";

                MailUtils.sendMail("285091197@qq.com",content,"我不爱你了");

                //用户名不存在调用方法存储用户注册信息
                dao.setUser(user);
                //注册成功返回即结果
                return true;
            }
            //存在该用户名注册失败

                return false;


    }

    /**
     * 调用方法使用激活码获取用户对象信息，
     * 根据对象掉方法激活用户账户
     * @param code
     * @return
     */
    @Override
    public boolean findUuid(String code) {
        User uuid = dao.findUuid(code);
        if (uuid==null){
            return false;
        }else {
            dao.activeUser(uuid);
            return true;
        }

    }

    /**
     * 账户名密码校验登录
     * @param user
     * @return
     */
    @Override
    public User loginUser(User user) {
        //通过账户密码查询数据
        User user1 =dao.finLoginUser(user);
        if (user1!=null){
            //验证通过
            return user1;
        }
        return null;
    }


}
