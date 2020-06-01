package com.hengshui.dao.impl;
import com.hengshui.dao.SellerDao;
import com.hengshui.domain.Seller;
import com.hengshui.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;


public class SellerDaoImpl implements SellerDao {

    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());


    @Override
    public Seller findById(int id) {
        String sql = "select * from tab_seller where sid = ?";

        return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<Seller>(Seller.class),id);
    }
}
