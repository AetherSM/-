package com.example.demo.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/*管理配置文件中jwt信息*/
@Component
@ConfigurationProperties(prefix = "jwt.admin")
@Data
public class JwtProperties {
    /**
     * 管理端管理员生成jwt令牌相关配置
     * */
    /*密钥*/
    private String adminSecretKey;
    /*jwt令牌有效期*/
    private long adminTtl;
    /*请求头,相应头标识*/
    private String adminTokenName;

}
