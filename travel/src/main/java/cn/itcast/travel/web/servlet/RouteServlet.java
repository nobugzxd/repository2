package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    /**
     * 查询具体线路的详情
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     *
     * 分析
     *  根据sql架构设计器得需要从页面获取到 rid和sid
     *  分别使用rid和sid查询到对应的详情图片和商家信息
     */


    /**
     * 获取service方法对象
     */
    RouteService service = new RouteServiceImpl();
    FavoriteService fs = new FavoriteServiceImpl();
    public void findRouteInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取到图片详情表的id
        String _rid = request.getParameter("rid");
        //获取到商家详情表的id
        String _sid = request.getParameter("sid");

        int rid = 0;
        int sid = 0;
        //判断rid有效
        if (_rid!=null&&_rid.length()>0&&!"null".equals(_rid)){rid = Integer.parseInt(_rid);}
        //判断sid有效
        if (_sid!=null&&_sid.length()>0&&!"null".equals(_sid)){sid = Integer.parseInt(_sid);}
        //将查询结果返回route对象保存数据
        Route route = service.findInfo(rid,sid);
        //将得到的route序列化为json对象并发送给浏览器
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf8");
        mapper.writeValue(response.getOutputStream(),route);

    }

    /**
     * 线路收藏
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void favoriteRoute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取rid查询来查看和添加收藏
        String rids = request.getParameter("rid");
        int rid = 0;

        if (rids!=null&&!"null".equals(rids)){
            rid = Integer.parseInt(rids);
        }
        User user = (User) request.getSession().getAttribute("user");
        //验证用户是否登录
        if (user==null){
            //用户未登录,条状让用户登录
           response.sendRedirect(request.getContextPath()+"/login.html");
            return;
        }
        int uid = user.getUid();

        //调业务区方法查询用户是否收藏
        boolean flag = fs.findFavorite(rid,uid);
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        System.out.println(flag);
        mapper.writeValue(response.getOutputStream(),flag);
    }

    public void favoriteCount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _rid = request.getParameter("rid");
        int rid = 0;

        if (_rid!=null&&!"null".equals(_rid)){
            rid = Integer.parseInt(_rid);
        }
        /*User user = (User) request.getSession().getAttribute("user");
        //验证用户是否登录
        if (user==null){
            //用户未登录,条状让用户登录
            response.sendRedirect(request.getContextPath()+"/login.html");
            return;
        }*/

        //调业务区方法查询产品收藏次数
        int count = fs.findCount(rid);
        System.out.println(count);
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),count);

    }


    /**
     * 加为收藏
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rids = request.getParameter("rid");
        int rid = 0;

        if (rids!=null&&!"null".equals(rids)){
            rid = Integer.parseInt(rids);
        }
        User user = (User) request.getSession().getAttribute("user");
        //验证用户是否登录
        System.out.println(user+"1111");
        if (user==null||"null".equals(user)||"".equals(user)){
            //用户未登录,条状让用户登录
            response.sendRedirect(request.getContextPath()+"/login.html");
            return;
        }
        int uid = user.getUid();
        fs.add(rid,uid);
    }


    /**
     * 删除收藏
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rids = request.getParameter("rid");
        int rid = 0;

        if (rids!=null&&!"null".equals(rids)){
            rid = Integer.parseInt(rids);
        }
        User user = (User) request.getSession().getAttribute("user");
        //验证用户是否登录
        if (user==null){
            //用户未登录,条状让用户登录
            response.sendRedirect(request.getContextPath()+"/login.html");
            return;
        }
        int uid = user.getUid();
        fs.delete(rid,uid);
    }
    }
