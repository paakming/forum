package com.wbm.forum.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author：Ming
 * @Date: 2022/12/11 14:52
 */
@Configuration
public class UploadConfig implements WebMvcConfigurer {
    private String uploadPath = "F://fileData/" ;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/image/**").addResourceLocations("file:"+uploadPath);
    }
}
