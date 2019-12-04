package com.cn.lucky.morning.model.web;

import com.cn.lucky.morning.model.web.intercept.AuthenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
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
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        //文件磁盘图片url 映射
//        //配置server虚拟路径，handler为前台访问的目录，locations为files相对应的本地路径
//        BaseQuery query = new BaseQuery();
//        query.setSize(Integer.MAX_VALUE);
//        String folder= PathTool.getProjectPath(SmartWaterBaseApplication.class) + "picture/";
//       registry.addResourceHandler("/picture/**").addResourceLocations(folder);
//    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/index");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    @Autowired
    AuthenInterceptor passportInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passportInterceptor).addPathPatterns(Arrays.asList("/**"));
    }
}