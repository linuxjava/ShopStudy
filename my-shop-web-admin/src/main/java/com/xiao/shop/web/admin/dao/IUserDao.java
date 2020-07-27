package com.xiao.shop.web.admin.dao;

import com.xiao.shop.domain.User;

public interface IUserDao {
    User getUser(String email, String password);
}
