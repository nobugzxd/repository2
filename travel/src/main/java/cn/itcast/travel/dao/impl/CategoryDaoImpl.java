package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /*
    public class CategoryDaoImpl implements CategoryDao {
        //创建jdbc数据库连接池对象

        /**
         * 查询所有路线游
         *
         * @return
         */
    @Override
    public List<Category> findAll() {
        //查询所有路线 创建sql
        String sql = "select * from tab_category order by cid asc ";
        //执行sql
        List<Category> list = template.query(sql, new BeanPropertyRowMapper<Category>(Category.class));

        return list;
    }

    /**
     * 分页功能查询总共信息条数
     *
     * @param cid
     * @param rname
     * @return
     */
    @Override
    public int findTotalCount(int cid, String rname) {
        String sql = "";
        StringBuilder sb = new StringBuilder();
        sql = "select Count(*) from tab_route where 1=1 ";
        List list = new ArrayList();
        sb.append(sql);
        if (cid != 0) {
            sb.append(" and cid = ? ");
            list.add(cid);
        }
        if (rname != null && rname.length() > 0&&!"null".equals(rname)) {

            sb.append(" and rname like ? ");

            list.add("%" + rname + "%");

        }
        sql = sb.toString();

        int count = template.queryForObject(sql, Integer.class, list.toArray());
        return count;
    }

    /**
     * 分页功能查询分页列表中的数据信息
     *
     * @param cid
     * @param start
     * @param pageSize
     * @param rname
     * @return
     */

    @Override
    public List<Route> findPageInfo(int cid, int start, int pageSize, String rname) {
        StringBuilder sb = new StringBuilder();
        List list = new ArrayList();
        try {
            String sql = "select * from tab_route where 1=1 ";
            sb.append(sql);
            if (cid != 0) {
                sb.append(" and cid = ? ");
                list.add(cid);
            }
            if (rname != null && rname.length() > 0&&!"null".equals(rname)) {
                sb.append(" and rname like ? ");

                list.add("%" + rname + "%");

            }
            sb.append(" limit ? , ? ");

            list.add(start);
            list.add(pageSize);
            List<Route> list1 = template.query(sb.toString(), new BeanPropertyRowMapper<Route>(Route.class), list.toArray());

            return list1;
        } catch (DataAccessException e) {
            return null;
        }
    }
}






