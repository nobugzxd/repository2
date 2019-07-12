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

@WebServlet("/registerUserServlet")
public class RegisterUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //接收数据字符集使用过滤器统一设置

        //校验验证码 1.1获取生成的验证码
        String code = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
        //1.2保证验证码的一次性删除验证码
        request.getSession().removeAttribute("CHECKCODE_SERVER");
        //获取页面输入的验证码
        String check = request.getParameter("check");
        if (code==null||!code.equalsIgnoreCase(check)){
            //验证码错误
            ResultInfo info = new ResultInfo();
            info.setErrorMsg("验证码错误");
            //将info对象序列化输出到浏览器
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
            return;
        }
            //验证码正确 验证密码

            Map<String, String[]> map = request.getParameterMap(); //1.接收页面提交参数

            User user = new User();//1.1创建user对象

            try {
                BeanUtils.populate(user,map);//1.3将接收的数据封装在user对象中
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            RegisterUserService service = new RegisterUserServiceImpl();//2创建serviced对象调用方法存储用户信息
            boolean flag = service.createUser(user); //2.2调取方法获取存储信息结果
            ResultInfo info = new ResultInfo();
            //判断结果

            if (flag){
                //注册成功
                info.setFlag(true);
            }else {
                //注册失败
                info.setFlag(false);
                info.setErrorMsg("注册失败！");
            }

            ObjectMapper mapper = new ObjectMapper();//创建json核心对象

            String json = mapper.writeValueAsString(info);//将info转换成json转发至浏览器
            response.setContentType("application/json;charset=utf-8");//设置响应字符集
            response.getWriter().write(json);//响应异步结果
        }





    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
