package com.hengshui.dao.impl;

import com.hengshui.dao.RouteImgDao;
import com.hengshui.domain.RouteImg;
import com.hengshui.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RouteImgDaoImpl implements RouteImgDao {

    JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<RouteImg> findByRid(int rid) {
        String sql = "select * from tab_route_img where rid = ?";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<RouteImg>(RouteImg.class),rid);
    }
}
