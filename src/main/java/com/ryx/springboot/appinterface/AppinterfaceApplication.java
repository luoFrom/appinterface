package com.ryx.springboot.appinterface;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * Spring将搜索 @EnableAutoConfiguration  注解的类所在包下的 @Entity  实体
 *  EnableAutoConfiguration 隐式地定义了一个基础的包搜索路径
 */

@SpringBootApplication
public class AppinterfaceApplication {

	public static void main(String[] args) {

		SpringApplication.run(AppinterfaceApplication.class, args);
	}
}
