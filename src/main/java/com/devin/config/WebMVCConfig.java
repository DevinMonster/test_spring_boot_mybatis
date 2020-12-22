package com.devin.config;

import com.devin.filters.JWTTokenFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截路径下的所有接口
        registry.addInterceptor(new JWTTokenFilter()).addPathPatterns("/**");
    }
}
