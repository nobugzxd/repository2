package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.ImgDao;
import cn.itcast.travel.domain.RouteImg;

import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * 详情图片
 */
public class ImgDaoImpl implements ImgDao {
    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 查询详情图片
     * @param rid
     * @return
     */
    @Override
    public List<RouteImg> findImg(int rid) {
        String sql = "select * from tab_route_img where rid = ?";
        List<RouteImg> list = template.query(sql, new BeanPropertyRowMapper<RouteImg>(RouteImg.class), rid);
        return list;
    }
}
