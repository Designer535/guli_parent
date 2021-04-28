package com.atguigu.acl.filter;

import com.atguigu.acl.security.TokenManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TokeAuthFilter extends BasicAuthenticationFilter {

    private TokenManager tokenManager;
    private RedisTemplate redisTemplate;

    public TokeAuthFilter(AuthenticationManager authenticationManager, TokenManager tokenManager, RedisTemplate redisTemplate) {
        super(authenticationManager);
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=getAuthentication(request);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token=request.getHeader("token");
        if(token!=null){
            String username=tokenManager.getUserFromToken(token);
            List<String> list = (List<String>) redisTemplate.opsForValue().get(username);
//            Collection<? extends GrantedAuthority>
            Collection<GrantedAuthority> collection=new ArrayList<>();
            for(String str:list){
                GrantedAuthority grantedAuthority=new SimpleGrantedAuthority(str);
                collection.add(grantedAuthority);
            }
            if(StringUtils.isEmpty(username)){
                return new UsernamePasswordAuthenticationToken(username,token,collection);
            }
            return null;
        }
        return null;
    }
}
