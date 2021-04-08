package com.atguigu.educms.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

@Configurable
public class MscConfig {

    @Bean
    public ISqlInjector getISqlInjector() {
        return new LogicSqlInjector();
    }
}
