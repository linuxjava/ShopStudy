package com.xiao.shop.web.admin.service;

import com.xiao.shop.domain.User;

public interface IUserService {
    User login(String email, String password);
}
