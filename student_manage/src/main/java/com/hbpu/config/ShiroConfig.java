package com.hbpu.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.hbpu.pojo.User;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("defaultWebSecurityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(defaultWebSecurityManager);
        Map<String,String> filterMap=new LinkedHashMap<>();
        //授权,未授权跳到未授权页面
//        filterMap.put("/user/add","perms[user:add]");
//        filterMap.put("/user/update","perms[user:update]");
//        filterMap.put("/index","perms[user:index]");

        //拦截
//        filterMap.put("/user/add","authc");
//        filterMap.put("/user/update","authc");
        filterMap.put("/index","authc");
        filterMap.put("/user/*","authc");
        filterMap.put("/zhuce","anon");
        filterMap.put("/login","anon");
        filterMap.put("/left","authc");
        filterMap.put("/desktop","authc");
        filterMap.put("/default","authc");

        bean.setFilterChainDefinitionMap(filterMap);
        //登陆的请求
        bean.setLoginUrl("/toLogin");
        //未授权请求
        bean.setUnauthorizedUrl("/noauth");
        bean.setSuccessUrl("/index");
        return bean;

    }

    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
            DefaultWebSecurityManager webSecurityManager = new DefaultWebSecurityManager();
            webSecurityManager.setRealm(userRealm);
            return webSecurityManager;
    }

    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }

    //整合ShiroDialect：整合thymeleaf+shiro
    @Bean
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }
}
