package com.hengshui.service;

import com.hengshui.domain.User;

public interface UserService {
    Boolean register(User user);

    boolean active(String code);

    User findById(String username);
}
