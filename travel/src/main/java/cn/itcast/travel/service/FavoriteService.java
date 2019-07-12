package cn.itcast.travel.service;

public interface FavoriteService {
    /**
     * 查询用户是否收藏了商品
     * @param rid
     * @param uid
     * @return
     */
    boolean findFavorite(int rid, int uid);

    /**
     * 查询商品被收藏的总次数
     * @param rid
     * @return
     */
    int findCount(int rid);

    /**
     * 增加收藏
     * @param rid
     * @param uid
     */
    void add(int rid, int uid);

    /**
     * 删除收藏
     * @param rid
     * @param uid
     */
    void delete(int rid, int uid);
}
