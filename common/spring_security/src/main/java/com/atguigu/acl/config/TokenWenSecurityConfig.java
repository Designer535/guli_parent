package com.atguigu.acl.config;

import com.atguigu.acl.filter.TokeAuthFilter;
import com.atguigu.acl.filter.TokenLoginFilter;
import com.atguigu.acl.security.DefaultPasswordEncoder;
import com.atguigu.acl.security.TokenLoginOutHandler;
import com.atguigu.acl.security.TokenManager;
import com.atguigu.acl.security.UnAuthEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class TokenWenSecurityConfig extends WebSecurityConfigurerAdapter {

    private RedisTemplate redisTemplate;
    private TokenManager tokenManager;
    private DefaultPasswordEncoder defaultPasswordEncoder;
    private UserDetailsService userDetailsService;

    @Autowired
    public TokenWenSecurityConfig(RedisTemplate redisTemplate, TokenManager tokenManager, DefaultPasswordEncoder defaultPasswordEncoder, UserDetailsService userDetailsService) {
        this.redisTemplate = redisTemplate;
        this.tokenManager = tokenManager;
        this.defaultPasswordEncoder = defaultPasswordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling()
                .authenticationEntryPoint(new UnAuthEntryPoint())
                .and().csrf().disable()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and().logout().logoutUrl("/admin/acl/index/logout")
                .addLogoutHandler(new TokenLoginOutHandler(tokenManager,redisTemplate)).and()
                .addFilter(new TokenLoginFilter(tokenManager,redisTemplate,authenticationManager()))
                .addFilter(new TokeAuthFilter(authenticationManager(),tokenManager,redisTemplate)).httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(defaultPasswordEncoder);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/api/**");
    }
}
