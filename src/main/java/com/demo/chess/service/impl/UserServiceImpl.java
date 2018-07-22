package com.demo.chess.service.impl;

import com.demo.chess.dao.IUserDao;
import com.demo.chess.model.User;
import com.demo.chess.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserDao userDao;

    @Override
    public User getUserByName(String userName) {
        logger.debug("igrsUserDao: {}", userDao);
        User user = userDao.getUserByName(userName);

        return user;
    }

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
}
