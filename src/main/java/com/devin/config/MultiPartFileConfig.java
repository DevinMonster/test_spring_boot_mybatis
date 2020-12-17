package com.devin.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

/**
 * 最大文件上传
 */
@Configuration
public class MultiPartFileConfig {

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        // 构造文件配置
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // 10MB的文件
        factory.setMaxFileSize(DataSize.ofMegabytes(10));
        // 单次请求大小 10MB
        factory.setMaxRequestSize(DataSize.ofMegabytes(11));
        return factory.createMultipartConfig();
    }
}
