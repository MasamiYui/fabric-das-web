package org.it611.das.config;

import org.it611.das.util.CookieUtil;
import org.it611.das.util.RedisUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object oobject) throws Exception {

        //获取token
        String token = CookieUtil.getCookie(request,CookieUtil.COOKIE_TOKEN_KEY);

        if( token.equals("") || token == null) {
            response.sendRedirect(request.getContextPath()+"/login/index");//跳转到登陆页
            return false;
        }

        //与redis中的数据进行比较
        Jedis redisClient = RedisUtil.getJedis();
        if(redisClient.get(token) == null || redisClient.get(token).equals("")) {
            response.sendRedirect(request.getContextPath()+"/login/index");//跳转到登陆页
            return false;
        }
        redisClient.close();
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
