package com.pangpan.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

/**
 * @author pangpan
 * @date 2021-01-14
 */
public interface LoadBalancer
{
    ServiceInstance instances(List<ServiceInstance> serviceInstances);

}
