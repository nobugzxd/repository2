package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    //创建dao对象
    CategoryDao dao = new CategoryDaoImpl();


    /**
     * 查询所有路线游集合
     *
     * @return
     */
    @Override
    public List<Category> findAll() {

        //先从redis中查询//创建Jedis对象
        Jedis jedis = JedisUtil.getJedis();
        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);
        List<Category> list = null;
        //redis中查询不到进行在mysql中查询
        if (categorys == null || categorys.size() == 0) {
            list = dao.findAll();
            //存储在redis数据库中
            for (int i = 0; i < list.size(); i++) {
                jedis.zadd("category", list.get(i).getCid(), list.get(i).getCname());
            }

        } else {
            //在redis中查询并封装在arrylist中
            //如果redis不为空将set集合中的数据存在list中
            list = new ArrayList<>();
            for (Tuple category : categorys) {
                Category category1 = new Category();
                category1.setCname(category.getElement());
                category1.setCid((int)category.getScore());
                list.add(category1);

            }
        }

        return list;


        //调用方法操作数据库查询

    }

    /**
     * 获取分页功能所需要的数据
     *
     * @param cid
     * @param pageSize
     * @param currentPage
     * @return
     */
    @Override
    public PageBean getPageInfo(int cid, int pageSize, int currentPage ,String rname) {
        //创建需要返回分页对象并添加属性
        PageBean pageBean = new PageBean();
/*        private int currentPage;//当前页
        private int totalPage;//总页数
        private int totalCount;//总记录数
        private List list;//分页列表数据
        private int pageSize;//每页显示条数*/

        //获取总记录数

        int totalCount = dao.findTotalCount(cid,rname);

        //开始数据条数
        int start = pageSize*(currentPage-1);

        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        //获取分页列表数据 参数为线路id 开始数据条数start 和每页显示条数
        List list = dao.findPageInfo(cid,start,pageSize, rname );
        pageBean.setCurrentPage(currentPage);
        pageBean.setList(list);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalCount(totalCount);
        pageBean.setTotalPage(totalPage);
        return pageBean;
    }
}
