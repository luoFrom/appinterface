package com.ryx.springboot.appinterface.web;

import com.ryx.springboot.appinterface.others.MyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {

    @RequestMapping("/index")
    public String index(ModelMap map){
        map.addAttribute("host","http://www.baidu.com");
        return "helloworld";
    }

    @RequestMapping("/err")
    public String testErr() throws Exception {
        throw new Exception("发生错误");
    }

    @RequestMapping("/errJson")
    public String errJson() throws MyException {
        throw new MyException("发生自定义异常错误");
    }

    @RequestMapping("/login")
    public  String login(){
        return "login";
    }
}
