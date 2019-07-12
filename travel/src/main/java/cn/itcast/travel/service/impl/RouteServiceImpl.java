package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.ImgDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.ImgDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {

    ImgDao imgDao = new ImgDaoImpl();

    SellerDao sellerDao = new SellerDaoImpl();
    RouteDao routeDao = new RouteDaoImpl();
    /**
     * 根据id分别查询对应的详情和详情图片
     *
     * @param rid 图片表
     * @param sid 卖家详情表
     * @return
     */
    @Override
    public Route findInfo(int rid, int sid) {
        //根据rid查询route对象
        Route route =routeDao.findOne(rid);

        //调用方法查询得到图片信息并封装
        List<RouteImg> list = imgDao.findImg(rid);
        route.setRouteImgList(list);
        //调用方法查询卖家信息并封装到对象
        Seller seller =sellerDao.findSala(sid);
        route.setSeller(seller);

        return route;
    }
}
