package com.ryx.springboot.appinterface.service.dubbo.impl;

import com.ryx.springboot.appinterface.service.dubbo.DemoServerService;

public class DemoServerServiceImpl implements DemoServerService {
    @Override
    public String sayHello(String name) {
        return "Hello word," + name ;
    }
}
