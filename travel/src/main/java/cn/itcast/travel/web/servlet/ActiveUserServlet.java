package cn.itcast.travel.web.servlet;

import cn.itcast.travel.service.RegisterUserService;
import cn.itcast.travel.service.impl.RegisterUserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/activeUserServlet")
public class ActiveUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        RegisterUserService rs = new RegisterUserServiceImpl();
        boolean flag =rs.findUuid(code);
        response.setContentType("text/html;charset=utf-8");
        if (flag){
            response.getWriter().write("激活成功！欢迎登陆<a href='http://192.168.22.53/travel/login.html'>登陆</a>");
        }else {
            response.getWriter().write("激活码错误，请联系管理员！");

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
