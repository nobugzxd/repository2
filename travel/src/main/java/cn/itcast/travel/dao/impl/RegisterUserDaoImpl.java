package cn.itcast.travel.dao.impl;


import cn.itcast.travel.dao.RegisterUserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class RegisterUserDaoImpl implements RegisterUserDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 查询用户名是否存在
     *
     * @param username
     * @return
     */
    @Override
    public User findByUsername(String username) {
        try {
//            创建sql
            String sql = "select * from tab_user where  username = ?";
            //执行sql
            return template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);

        } catch (DataAccessException e) {

        }
        return null;
    }

    /**
     * 存储新用户信息到数据库
     *
     * @param user
     */
    /*
    uid   username   PASSWORD    NAME    birthday     sex    telephone    email     STATUS    CODE
     */
    @Override
    public void setUser(User user) {
        //创建sql
        String sql = "insert into tab_user values(?,?,?,?,?,?,?,?,?,?);";
        //执行sql
        template.update(sql, null, user.getUsername(), user.getPassword(), user.getName(), user.getBirthday(), user.getSex(), user.getTelephone(), user.getEmail(), user.getStatus(), user.getCode());
    }

    /**
     * 根据用户激活码确认用户并返回用户对象
     *
     * @param code
     * @return
     */

    @Override
    public User findUuid(String code) {
        try {
//            创建sql
            String sql = "select * from tab_user where  code = ?";
            //执行sql
            return template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), code);

        } catch (DataAccessException e) {

            return null;
        }

    }

    /**
     * 修改status状态激活用户账户
     *
     * @param user
     */

    public void activeUser(User user) {
        String sql = "update tab_user set status = 'Y' where uid = ?";
        template.update(sql, user.getUid());
    }

    /**
     * 登录验证
     * @param user
     * @return
     */
    @Override
    public User finLoginUser(User user) {
        try {
            String sql = "select * from tab_user where username = ? and password = ?";
            User user1 = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), user.getUsername(), user.getPassword());
            return user1;
        } catch (DataAccessException e) {

        }
        return null;

    }
}
