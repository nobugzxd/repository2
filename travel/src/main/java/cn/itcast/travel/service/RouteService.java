package cn.itcast.travel.service;

import cn.itcast.travel.domain.Route;


public interface RouteService {
    /**
     * 根据id查询对应的商品详情
     * @param rid
     * @param sid
     * @return
     */
    Route findInfo(int rid, int sid);
}
