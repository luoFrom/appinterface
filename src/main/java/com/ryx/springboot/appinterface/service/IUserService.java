package com.ryx.springboot.appinterface.service;

import com.ryx.springboot.appinterface.domain.User;
import java.util.List;

public interface IUserService {
    User findByName(String name);

    User findById( Long id);

    List<User> findAllUser();

    void save(User user);

    void delete(Long id);
}
