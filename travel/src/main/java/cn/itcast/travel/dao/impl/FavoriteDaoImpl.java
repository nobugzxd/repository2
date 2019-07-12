package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;

public class FavoriteDaoImpl implements FavoriteDao {
    //获取连接池对象
    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public boolean findFavorite(int rid, int uid) {
        String sql = "select count(*) from tab_favorite where rid = ? and uid = ? ";
        try {
            int count = template.queryForObject(sql, Integer.class, rid, uid);
            //判断是否收藏
            if (count != 0) {
                //已经收藏
                return true;
            }
        } catch (DataAccessException e) {
        }
        return false;
    }

    @Override
    public int findCount(int rid) {

        String sql = "select count(*) from tab_favorite where rid = ?  ";

        int count = template.queryForObject(sql, Integer.class, rid);
        //返回用户收藏的总次数

        return count;

    }

    /**
     * 添加收藏
     * @param rid
     * @param uid
     */
    @Override
    public void add(int rid, int uid) {
        String sql = "insert into tab_favorite values(?, ? ,?)";
        template.update(sql,rid,new Date(),uid);
    }

    /**
     * 删除收藏
     * @param rid
     * @param uid
     */
    @Override
    public void delete(int rid, int uid) {
        String sql = "delete from tab_favorite where rid = ? and uid = ?";
        template.update(sql,rid,uid);
    }
}
