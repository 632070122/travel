package com.hengshui.service.impl;

import com.hengshui.dao.UserDao;
import com.hengshui.dao.impl.UserDaoImpl;
import com.hengshui.domain.User;
import com.hengshui.service.UserService;
import com.hengshui.util.MailUtils;
import com.hengshui.util.UuidUtil;

public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();

    @Override
    public Boolean register(User user) {

        User u = userDao.findByUserName(user.getUsername());

        if (u != null) {
            return false;
        }

        user.setCode(UuidUtil.getUuid());  //存储激活码

        user.setStatus("N"); //激活状态

        String content = "<a href = 'http://192.168.0.140:80/travel/user/active?code=" + user.getCode() + "'>点击激活[黑马旅游网]</a>";

        MailUtils.sendMail(user.getEmail(), content, "激活邮件");

        userDao.save(user);

        return true;


    }

    @Override
    public boolean active(String code) {

        User user = userDao.findByCode(code);

        if (user != null) {

            userDao.updateStatus(user);
            return true;
        } else {

            return false;
        }

    }

    @Override
    public User findById(String username) {

        return userDao.findByUserName(username);
    }


}
