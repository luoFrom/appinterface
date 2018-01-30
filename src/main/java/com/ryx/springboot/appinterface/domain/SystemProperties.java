package com.ryx.springboot.appinterface.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *  注入带前缀的属性 @ConfigurationProperties
 */
@Component
@ConfigurationProperties(prefix = "com.ryx")
public class SystemProperties {
    @Value("${appName}")
    private String appName;
    @Value("${company}")
    private String company;


    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "SystemProperties{" +
                "appName='" + appName + '\'' +
                ", company='" + company + '\'' +
                '}';
    }

    public SystemProperties() {
    }
}
