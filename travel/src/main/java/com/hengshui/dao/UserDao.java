package com.hengshui.dao;

import com.hengshui.domain.User;

public interface UserDao {

    User findByUserName(String username);

    void save(User user);

    User findByCode(String code);

    void updateStatus(User user);
}
