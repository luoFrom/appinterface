package com.ryx.springboot.appinterface;


import com.ryx.springboot.appinterface.domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest( classes = AppinterfaceApplication.class)
public class TestRedis {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test() throws  Exception{
        stringRedisTemplate.opsForValue().set("user","luo");

        System.out.println("user:"+stringRedisTemplate.opsForValue().get("user"));
        Assert.assertEquals("luo",stringRedisTemplate.opsForValue().get("user"));
    }

    @Test
    public void testObj() throws  Exception{
        User user=new User();
        user.setAddTime(new Date());
        user.setName("luo");

        ValueOperations<String,User> vo=redisTemplate.opsForValue();
        vo.set("com.ryx.user",user);
        vo.set("com.ryx.test",user,1, TimeUnit.SECONDS);
        Thread.sleep(1000);

        boolean exists=redisTemplate.hasKey("com.ryx.test");
        if(exists){
            System.out.println("exists is true");
            String name= vo.get("com.ryx.test").getName();
            System.out.println("name :"+name);
        }else{
            System.out.println(" exists is false");
        }
    }

}
