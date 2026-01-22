package com.example.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("校园生活服务平台 API")
                        .description("校园生活服务平台后端接口文档，包含用户、跑腿、优惠券、二手求购、聊天、投诉等功能模块。")
                        .version("v1.0.0"));
    }
}
