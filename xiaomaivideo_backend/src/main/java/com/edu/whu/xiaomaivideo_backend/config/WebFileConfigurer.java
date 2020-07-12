/**
 * Author: 张俊杰
 * Create Time: 2020/7/10
 * Update Time: 2020/7/10
 */

package com.edu.whu.xiaomaivideo_backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootConfiguration
public class WebFileConfigurer implements WebMvcConfigurer {
    @Value("${constant.pic-file-resource-path}")
    private String path;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/api/file/**").addResourceLocations("file:" + path);
    }
}