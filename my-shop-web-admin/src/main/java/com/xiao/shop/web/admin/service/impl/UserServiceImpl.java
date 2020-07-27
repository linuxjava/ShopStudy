package com.xiao.shop.web.admin.service.impl;

import com.xiao.shop.domain.User;
import com.xiao.shop.web.admin.dao.IUserDao;
import com.xiao.shop.web.admin.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService{
    @Autowired
    private IUserDao userDao;

    public User login(String email, String password) {
        return userDao.getUser(email, password);
    }
}
