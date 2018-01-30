package com.ryx.springboot.appinterface;

import com.ryx.springboot.appinterface.web.UserController;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 *    指定加载应用程序上下文 @ContextConfiguration 不能完整的加载spring boot特性
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AppinterfaceApplication.class)
@WebAppConfiguration
public class AppUserTestTest {

    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        mvc= MockMvcBuilders.standaloneSetup(new UserController()).build();

    }

    public void testUserController() throws  Exception{

        RequestBuilder request=null;



    }
}