package com.xiao.shop.web.admin.dao.impl;

import com.xiao.shop.domain.User;
import com.xiao.shop.web.admin.dao.IUserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements IUserDao{
    private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    public User getUser(String email, String password) {
        logger.debug("调用getUser, email:{}, password:{}", email, password);

        if("123@qq.com".equalsIgnoreCase(email) && "123456".equals(password)){
            User user = new User("123@qq.com", "测试");
            logger.info("成功获取{}信息", user.getUsername());
            return user;
        }
        logger.warn("获取{}信息失败", email);
        return null;
    }
}
