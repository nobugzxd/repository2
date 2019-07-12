package cn.itcast.travel.dao;

public interface FavoriteDao {
    /**
     * 操作数据库查询用户是否收藏对应线路
     * @param rid
     * @return
     */
    boolean findFavorite(int rid,int uid);

    /**
     * 查询商品收藏的总次数
     * @param rid
     * @return
     */
    int findCount(int rid);

    /**
     * 添加收藏
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
