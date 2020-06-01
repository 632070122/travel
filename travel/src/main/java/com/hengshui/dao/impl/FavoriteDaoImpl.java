package com.hengshui.dao.impl;

import com.hengshui.dao.FavoriteDao;
import com.hengshui.domain.Favorite;
import com.hengshui.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;

public class FavoriteDaoImpl implements FavoriteDao {

    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());



    @Override
    public Favorite findByRidAndUid(int rid, int uid) {

        Favorite favorite = null;
        try {
            String sql = "select * from tab_favorite where rid = ? and uid = ?";
            favorite = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<Favorite>(Favorite.class),rid,uid);
            return favorite;
        }catch (Exception e){

        }

        return favorite;
    }

    @Override
    public int findCountByRid(int rid) {
        String sql = "select count(*) from tab_favorite where rid = ?";
        return jdbcTemplate.queryForObject(sql,Integer.class,rid);
    }

    @Override
    public void add(int rid, int uid) {
        String sql = "insert into tab_favorite values(?,?,?)";
        jdbcTemplate.update(sql,rid,new Date(),uid);
    }

}
