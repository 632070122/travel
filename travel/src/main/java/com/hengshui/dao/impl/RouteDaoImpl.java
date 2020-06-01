package com.hengshui.dao.impl;

import com.hengshui.dao.RouteDao;
import com.hengshui.domain.Route;
import com.hengshui.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {

    JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public int findTotalCount(int cid,String rname) {
       String sql = "select count(*) from tab_route where 1 = 1 ";
       StringBuilder sb = new StringBuilder(sql);
       List params = new ArrayList();
       if(cid != 0){
           sb.append(" and cid = ? ");
           params.add(cid);
       }
       if(rname != null && rname.length() >0){
           sb.append(" and rname like ?");
           params.add("%"+rname+"%");
       }
       sql = sb.toString();
       return jdbcTemplate.queryForObject(sql,Integer.class,params.toArray());
    }

    @Override
    public List<Route> findByPage(int cid, int start, int pageSize,String rname) {
        String sql = " select * from tab_route where 1 = 1 ";
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList();
        if(cid != 0){
            sb.append(" and cid = ? ");
            params.add(cid);
        }
        if(rname != null && rname.length() >0){
            sb.append(" and rname like ?");
            params.add("%"+rname+"%");
        }

        sb.append(" limit ? , ? ");
        sql = sb.toString();
        params.add(start);
        params.add(pageSize);



        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<Route>(Route.class),params.toArray());
    }

    @Override
    public Route findOne(int rid) {
        String sql = "select * from tab_route where rid = ?";
        return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<Route>(Route.class),rid);
    }
}
