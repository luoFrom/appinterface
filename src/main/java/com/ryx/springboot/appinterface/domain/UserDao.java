package com.ryx.springboot.appinterface.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<User,Long>{

    User findByName(String name);

    @Query("select u from User u where u.name=:name")
    User findUser(@Param("name") String name);

    User findById(Long id);

    @Query("select u from User u order by u.addTime")
    List<User> findAllUser();
}
