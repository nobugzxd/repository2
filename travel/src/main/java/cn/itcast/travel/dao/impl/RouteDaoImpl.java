package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class RouteDaoImpl implements RouteDao {
    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public Route findOne(int rid) {
        String sql = "select * from tab_route where rid = ?";
        Route route = template.queryForObject(sql, new BeanPropertyRowMapper<Route>(Route.class),rid);
        return  route;
    }
}
