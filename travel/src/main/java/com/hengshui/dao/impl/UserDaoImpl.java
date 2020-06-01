package com.hengshui.dao.impl;

import com.hengshui.dao.UserDao;
import com.hengshui.domain.User;
import com.hengshui.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {

    JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public User findByUserName(String username) {
        User user = null;
        try {
            String sql = "select * from tab_user where username = ?";
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);

        }catch (Exception e){

        }

        return user;
    }

    @Override
    public void save(User user) {

        String sql = "insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code)values(?,?,?,?,?,?,?,?,?)";

        jdbcTemplate.update(sql,user.getUsername(),user.getPassword(),user.getName(),user.getBirthday(),user.getSex(),user.getTelephone(),user.getEmail(),user.getStatus(),user.getCode());


    }

    @Override
    public User findByCode(String code) {

        User user = null;

        String sql = "select * from tab_user where code = ?";

        try {
            user = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),code);

        }catch (Exception e){

        }

        return user;
    }

    @Override
    public void updateStatus(User user) {
        String sql = "update tab_user set status = 'Y' where uid = ?";
        jdbcTemplate.update(sql,user.getUid());
    }
}
