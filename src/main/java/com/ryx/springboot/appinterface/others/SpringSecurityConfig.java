package com.ryx.springboot.appinterface.others;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @EnableWebSecurity注解开启Spring Security的功能
 * 继承WebSecurityConfigurerAdapter，并重写它的方法来设置一些web安全的细节
 */

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

/**
 *authorizeRequests()定义哪些URL需要被保护、哪些不需要被保护
 * @param security
 * @throws Exception
 */
    @Override
    protected  void configure(HttpSecurity security) throws Exception {
        security.authorizeRequests()
                //指定了/和/home不需要任何认证就可以访问,
                //其他的路径都必须通过身份验证
                .antMatchers("/","/home").permitAll()
                .anyRequest().authenticated()
                //定义当需要用户登录时候，转到的登录页面
                .and().formLogin().loginPage("/login").permitAll()
                .and().logout().permitAll();
    }

    /**
     * 在内存中创建了一个用户，该用户的名称为user，密码为password，用户角色为USER
     * @param auth
     * @throws Exception
     */
    @Autowired
    public void  configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password("123").roles("ADMIN");


    }
}
