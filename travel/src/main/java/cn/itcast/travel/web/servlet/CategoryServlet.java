package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.service.impl.CategoryServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {

    //创建service业务对象
    CategoryService service = new CategoryServiceImpl();


    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //调用service方法查询所有路线信息
        List<Category> list = service.findAll();

        //将list集合序列化传给浏览器
        ObjectMapper mapper = new ObjectMapper();
        //设置响应字符集并将json对象传送给浏览器
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),list);

    }

    /**
     * 分页查询功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _cid = request.getParameter("cid");//从页面获取cid    并将数据转换成int类型
        String _pageSize = request.getParameter("pageSize");//从页面获取每页数据条数
        String _currentPage = request.getParameter("currentPage");//获取当前页
        //接收线路名称
        String rname = request.getParameter("rname");
        if (rname!=null){
            rname = new String(rname.getBytes("iso-8859-1"),"utf-8");
        }


        //定义变量
        int pageSize;
        int currentPage;
        int cid =  0;
         /*cid = Integer.parseInt(_cid);*/
        if (_cid!=null&&_cid.length()!=0&&!"null".equals(_cid)){ cid=Integer.parseInt(_cid); }
        //判断每页信息数是否为空 为空则进行初始化
        if (_pageSize==null||_pageSize.length()==0){ pageSize=5; }else {pageSize=Integer.parseInt(_pageSize);}
        //判断当前页是否为空 为空则进行初始化
        if (_currentPage==null||_currentPage.length()==0){ currentPage=1;}else {currentPage = Integer.parseInt(_currentPage);}
        //调用service方法获得pagebean对象
        PageBean<Route> pageBean =service.getPageInfo(cid,pageSize,currentPage,rname);
        //创建核心对象将对象序列化为json对象并转发给浏览器
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),pageBean);

    }


    }
