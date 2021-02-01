package com.pangpan.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author pangpan
 * 自定义的负载均衡策略
 * @date 2021-01-14
 */
@Configuration
public class MySelfRule {

    @Bean
    public IRule myRule(){
        return  new RandomRule();//定义为随机
    }



}
