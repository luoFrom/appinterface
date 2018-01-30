package com.ryx.springboot.appinterface.service.impl;

import com.ryx.springboot.appinterface.domain.User;
import com.ryx.springboot.appinterface.domain.UserDao;
import com.ryx.springboot.appinterface.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User findByName(String name) {
        return userDao.findByName(name);
    }

    @Override
    public User findById(Long id) {
        return userDao.findById(id);
    }


    @Override
    public List<User> findAllUser() {
        return userDao.findAllUser();
    }

    @Override
    public void save(User user) {
        userDao.save(user);
    }

    @Override
    public void delete(Long id) {
        userDao.delete(id);
    }
}
