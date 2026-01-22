package com.example.demo.config;

import com.example.demo.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web配置类
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private JwtInterceptor jwtInterceptor;

    /**
     * 配置拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**") // 拦截所有请求
                .excludePathPatterns(
                        "/auth/register",  // 排除注册接口
                        "/auth/login",     // 排除登录接口
                        "/auth/logout",    // 排除退出接口
                        "/auth/query",     // 排除查询接口
                        "/auth/query/**",
                        "/doc.html",                // Knife4j 接口文档
                        "/webjars/**",              // 静态资源
                        "/swagger-resources/**",    // Swagger 资源
                        "/v3/api-docs/**",          // API 文档数据
                        "/swagger-ui/**",           // Swagger UI 资源
                        "/swagger-ui.html"          // Swagger UI 页面
                );
    }

    /**
     * 配置跨域
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
