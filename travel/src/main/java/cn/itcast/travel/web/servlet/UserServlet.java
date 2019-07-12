package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.RegisterUserService;
import cn.itcast.travel.service.impl.RegisterUserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*")//虚拟路径 /traver/user/*
public class UserServlet extends BaseServlet {
    //声明service业务对象
    private RegisterUserService service = new RegisterUserServiceImpl();

    /**
     * 注册功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //接收数据字符集使用过滤器统一设置

        //校验验证码 1.1获取生成的验证码
        String code = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
        //1.2保证验证码的一次性删除验证码
        request.getSession().removeAttribute("CHECKCODE_SERVER");
        //获取页面输入的验证码
        String check = request.getParameter("check");
        if (code == null || !code.equalsIgnoreCase(check)) {
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
            BeanUtils.populate(user, map);//1.3将接收的数据封装在user对象中
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //2创建serviced对象调用方法存储用户信息
        boolean flag = service.createUser(user); //2.2调取方法获取存储信息结果
        ResultInfo info = new ResultInfo();
        //判断结果

        if (flag) {
            //注册成功
            info.setFlag(true);
        } else {
            //注册失败
            info.setFlag(false);
            info.setErrorMsg("注册失败！");
        }

        ObjectMapper mapper = new ObjectMapper();//创建json核心对象

        String json = mapper.writeValueAsString(info);//将info转换成json转发至浏览器
        response.setContentType("application/json;charset=utf-8");//设置响应字符集
        response.getWriter().write(json);//响应异步结果
    }


    /**
     * 登录后注销功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void outLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 用户退出删除session
         */
        request.getSession().invalidate();
        //重定向页面;
        response.sendRedirect(request.getContextPath()+"/login.html");
    }

    /**
     * 登录功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

    /**
     * 页头信息回显功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取session域中的user对象
        User user = (User) request.getSession().getAttribute("user");
        //将数据序列化到浏览器，创建核心对象
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),user);

    }

    /**
     * 激活账户功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        boolean flag =service.findUuid(code);
        response.setContentType("text/html;charset=utf-8");
        if (flag){
            response.getWriter().write("激活成功！欢迎登陆<a href='login.html'>登陆</a>");
        }else {
            response.getWriter().write("激活码错误，请联系管理员！");

        }
    }
}
