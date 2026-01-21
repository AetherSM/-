package com.example.demo.interceptor;

import com.example.demo.context.BaseContext;
import com.example.demo.expection.TokenNullException;
import com.example.demo.properties.JwtProperties;
import com.example.demo.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * JWT拦截器
 */
@Component
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /*获取到当前线程的id*/
        long id = Thread.currentThread().getId();
        log.info("当前线程id为{}", id);

        //HandlerMethod是封装控制器中具体的方法信息,这里就是检查是不是Controller方法
        if (!(handler instanceof HandlerMethod)) {
            //当前拦截到的不是动态方法，直接放行
            return true;
        }

        /*获得请求头中的token信息*/
        String token = request.getHeader(jwtProperties.getAdminTokenName());
        log.info("请求令牌: {}", token);
        /*检查是否携带令牌*/
        if (token == null || token.isEmpty()) {
            log.info("携带令牌为空");
            throw new TokenNullException("携带令牌为空");
        }

        try {
            /*解析令牌*/
            log.info("开始解析令牌...");
            Claims claims = JwtUtil.parseJWT(jwtProperties.getAdminSecretKey(), token);
            log.info("获得到令牌信息{}", claims);

            /*获取到当前用户的ID*/
            Long adminId = Long.parseLong(claims.getId());
            log.info("当前用户ID为{}", adminId);
            BaseContext.setCurrentId(adminId);

            /*获取到当前用户的用户名*/
            String username = claims.getSubject();
            log.info("当前用户用户名为{}", username);
            BaseContext.setCurrentId(Long.valueOf(username));
            log.info("令牌校验通过");
            return true;
        } catch (NumberFormatException e) {
            response.setStatus(401);
            log.info("令牌解析失败");
            return false;
        }

    }
}

