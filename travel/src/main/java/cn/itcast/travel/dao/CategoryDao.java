package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.domain.Route;

import java.util.List;

public interface CategoryDao {
    /**
     * 查询所有路线游
     * @return
     */
    List<Category> findAll();

    /**
     * 分页功能查询总信息条数
     * @param cid
     * @param rname
     * @return
     */
    int findTotalCount(int cid, String rname);

    /**
     * 分页功能查询分页列表中的数据信息
     * @param cid
     * @param start
     * @param pageSize
     * @param rname
     * @return
     */
    List<Route> findPageInfo(int cid, int start, int pageSize, String rname);
}
