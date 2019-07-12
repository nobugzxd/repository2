package cn.itcast.travel.service;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

import java.util.List;

public interface CategoryService {
    /**
     * 查询所有线路
     * @return
     */
    List<Category> findAll();

    /**
     * 分页功能
     * @param cid
     * @param pageSize
     * @param currentPage
     * @return
     */
    PageBean<Route> getPageInfo(int cid, int pageSize, int currentPage,String rname);
}
