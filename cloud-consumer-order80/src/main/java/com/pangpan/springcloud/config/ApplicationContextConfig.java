package com.pangpan.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContextConfig {

    @Bean
//    @LoadBalanced//负载均衡，不然找不到集群中哪个地址的服务
    public RestTemplate getRestTemplate()
    {
        return new RestTemplate();
    }
}
