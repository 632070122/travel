package com.hengshui.dao.impl;

import com.hengshui.dao.CategoryDao;
import com.hengshui.domain.Category;
import com.hengshui.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CategoryDaoImpl implements CategoryDao {

    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<Category> findAll() {
        String sql = "select * from tab_category";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<Category>(Category.class));
    }
}
