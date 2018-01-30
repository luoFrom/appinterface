package com.ryx.springboot.appinterface.others;

/**
 * 自定义异常类，用来捕获异常，并返回json
 */
public class MyException extends Exception {

    public  MyException(String message) {
        super(message);
    }
}
