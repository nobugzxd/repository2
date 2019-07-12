package cn.itcast.travel.dao;

import cn.itcast.travel.domain.RouteImg;


import java.util.List;

/**
 * \查询详情图片
 */
public interface ImgDao {
    List<RouteImg> findImg(int rid);
}
