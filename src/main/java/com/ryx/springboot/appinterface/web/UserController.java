package com.ryx.springboot.appinterface.web;

import com.ryx.springboot.appinterface.domain.User;
import com.ryx.springboot.appinterface.service.IUserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通过@ApiOperation注解来给API增加说明、通过@ApiImplicitParams、@ApiImplicitParam注解来给参数增加说明。
 */
@RestController
@RequestMapping("/user")
public class UserController {
    /**
     * 创建线程安全的Map
     */
    private static Map<Long,User> users= Collections.synchronizedMap(new HashMap<Long,User>());

    @Autowired
    private IUserService userService;

    @ApiOperation(value = "获取用户列表",notes = "测试")
    @RequestMapping(value = "",method = RequestMethod.GET)
    public List<User> getUserList(){
        List<User> list=userService.findAllUser();
        return  list;
    }

    @RequestMapping(value = "/",method = RequestMethod.POST)
    public String postUser(@ModelAttribute User user){

        userService.save(user);
        return "success";
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public User getUserById(@PathVariable Long id){

        return userService.findById(id);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    })
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public String putUser(@PathVariable Long id,@ModelAttribute User user){
        User u=users.get(id);
        u.setName(user.getName());
        u.setLoginName(user.getLoginName());
        u.setPassword(user.getPassword());
        u.setStatus(user.getStatus());
        userService.save(user);
        return "success";
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable Long id){

        userService.delete(id);
        return "success";
    }

}
