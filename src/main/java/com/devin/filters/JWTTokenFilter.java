package com.devin.filters;

import com.devin.enums.ApiEnum;
import com.devin.exception.GlobalException;
import com.devin.utils.JWTUtil;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JWTTokenFilter extends HandlerInterceptorAdapter {

    public static final String NOLOGIN = "/user/login,/user/register,/user/code";
    private static final String[] lst = NOLOGIN.split(",");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return check(request, response, handler);
    }

    private boolean check(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 获取JWT信息
        String servletPath = request.getServletPath(); // 接口
        // 判断请求的接口是否需要登录
        for (String s : lst) if (s.equals(servletPath)) return true;
        // 解析
        Claims claims = verifyJWTToken(request);
        if (claims == null) throw new GlobalException(ApiEnum.TOKEN_NOT_MATCH);
        Long userId = claims.get("userId", Long.class);
        String username = claims.get("username", String.class);
        request.setAttribute("userId", userId);
        request.setAttribute("username", username);
        // 鉴权
        // 签名校验
        // 数据解析重取
        return true;
    }

    private Claims verifyJWTToken(HttpServletRequest request) {
        String header = request.getHeader(JWTUtil.HEADER);
        Claims claims;
        try {
            claims = JWTUtil.checkToken(header, null);
        } catch (Exception e) {
            throw new GlobalException(ApiEnum.TOKEN_NOT_MATCH);
        }
        return claims;
    }
}
