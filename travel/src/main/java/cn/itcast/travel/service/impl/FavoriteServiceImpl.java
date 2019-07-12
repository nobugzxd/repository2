package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.service.FavoriteService;

public class FavoriteServiceImpl implements FavoriteService {
    FavoriteDao dao = new FavoriteDaoImpl();
    /**
     * 查询用户是否收藏
     *
     * @param
     * @param rid
     * @return
     */
    @Override
    public boolean findFavorite(int rid, int uid) {
        //调取dao出操作查询数据库
       return dao.findFavorite(rid,uid);

    }

    /**
     * 查询产品被用户收藏的总次数
     * @param rid
     * @return
     */
    @Override
    public int findCount(int rid) {
        return dao.findCount(rid);

    }

    /**
     * 添加收藏
     * @param rid
     * @param uid
     */
    @Override
    public void add(int rid, int uid) {
        dao.add(rid,uid);
    }

    /**
     * 删除收藏
     * @param rid
     * @param uid
     */
    @Override
    public void delete(int rid, int uid) {
        dao.delete(rid,uid);
    }
}
