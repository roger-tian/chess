package com.demo.chess.dao;

import com.demo.chess.model.User;

public interface IUserDao {
    User getUserByName(String user);
}