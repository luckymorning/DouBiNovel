package com.cn.lucky.morning.model.web;

import com.cn.lucky.morning.model.common.base.BaseQuery;
import com.cn.lucky.morning.model.web.interceptor.AuthenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration

public class WebMvcConfiguration  implements WebMvcConfigurer {
    /*@Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        //开启路径后缀匹配
        configurer.setUseRegisteredSuffixPatternMatch(true);
    }

    @Configuration
    public class WebMvcConfg extends WebMvcConfigurationSupport {
        //省略
    }
    */

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver getCommonsMultipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(20971520);
        multipartResolver.setMaxInMemorySize(1048576);
        return multipartResolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //文件磁盘图片url 映射
        //配置server虚拟路径，handler为前台访问的目录，locations为files相对应的本地路径
        BaseQuery query = new BaseQuery();
        query.setSize(Integer.MAX_VALUE);

       registry.addResourceHandler("/picture/**").addResourceLocations("file:D:/Images/").addResourceLocations("file:E:/Clip/");

    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/index");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);

    }

    @Autowired
    AuthenInterceptor passportInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        BaseQuery query = new BaseQuery();
        query.setSize(Integer.MAX_VALUE);
        List<String> listPatterns = new ArrayList<>();
        listPatterns.add("/css/**");
        listPatterns.add("/fonts/**");
        listPatterns.add("/images/**");
        listPatterns.add("/js/**");
        listPatterns.add("/lib/**");
        listPatterns.add("/login");
        registry.addInterceptor(passportInterceptor).addPathPatterns(Arrays.asList("/**")).excludePathPatterns(listPatterns);//Arrays.asList("/welcome", "/datum/**", "/picture/**","/api/**", "/admin/doLogout", "/admin/doLogin","/admin/login","/css/**", "/fonts/**", "/images/**", "/js/**", "/lib/**", "/login"));
    }
}