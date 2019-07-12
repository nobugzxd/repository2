package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.RegisterUserService;
import cn.itcast.travel.service.impl.RegisterUserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * 用户登陆
 */
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取用户登录信息
        Map<String, String[]> map = request.getParameterMap();
        User user = new User();//创建user对象封装页面获取的数据
        try {
            BeanUtils.populate(user,map);//将获取的map集合数据封装在user中
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //2..调用service
        RegisterUserService service = new RegisterUserServiceImpl();
        User user1 = service.loginUser(user);//调用login方法进行登录验证账户名和密码；

        ResultInfo info = new ResultInfo();
        if (user1!=null){
            //账号密码验证通过，验证激活状态
            if ("Y".equals(user1.getStatus())){
                //已经激活登陆成功
                info.setFlag(true);
                //将激活状态用户存入session域
                request.getSession().setAttribute("user",user1);
            }else{
                //未激活请先激活
                info.setFlag(false);
                info.setErrorMsg("请激活之后再次登录！");
            }
        }else {
            //账户名密码错误
            info.setFlag(false);
            info.setErrorMsg("账户或密码不正确！");
        }
        //3.使用json将将返回的数据序列化转发给浏览器
        //3.1创建json核心对象
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),info);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
