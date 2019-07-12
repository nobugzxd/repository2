package cn.itcast.travel.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;


public class BaseServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //进行方法分发
        //1.获取请求路径 url从虚拟路径开始 urL完整路径
        String uri = request.getRequestURI();

        //2获取方法名称    lastIndexOf获取最后一个/的索引+1就是方法名
        String methodName = uri.substring(uri.lastIndexOf("/")+1 );


        //3获取方法对象，  是子类调用时获取的是子类对象
        try {
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(this, request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
