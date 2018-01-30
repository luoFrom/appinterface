package com.ryx.springboot.appinterface;

import com.ryx.springboot.appinterface.domain.User;
import com.ryx.springboot.appinterface.domain.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AppinterfaceApplication.class)
@WebAppConfiguration
public class AppUserTest {
    @Autowired
    private UserDao userDao;

    @Test
    public  void test() throws Exception {
        User user=new User();
        user.setName("aaa");
        user.setStatus("1");
        user.setAddTime(new Date());
        userDao.save(user);
        user.setId(null);
        user.setName("bbb");
        user.setAddTime(new Date());
        userDao.save(user);

        List<User> list=userDao.findAllUser();
        for ( User u : list ) {
           System.out.println("user:"+u);
        }

    }
}
