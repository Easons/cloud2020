package com.pangpan.springcloud.controller;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import com.pangpan.springcloud.entities.CommonResult;
import com.pangpan.springcloud.entities.Payment;
import com.pangpan.springcloud.lb.LoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
public class OrderController
{

//    public  static  final String PAYMENT_URL = "http://localhost:8001";//单机版本
    public  static  final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";//集群版本


    @Resource
    RestTemplate restTemplate;

    @Autowired
    LoadBalancer loadBalancer;

    @Resource
    DiscoveryClient discoveryClient;

    @GetMapping("/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment){
        return restTemplate.postForObject(PAYMENT_URL+"/payment/create",payment,CommonResult.class);
    }

    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id)
    {
        return restTemplate.getForObject(PAYMENT_URL+"/payment/get/"+id,CommonResult.class);
    }

    @GetMapping(value = "/consumer/payment/lb")
    public String getPaymentLB()
    {

        List<ServiceInstance> instances = discoveryClient.getInstances("");

        if(instances == null || instances.size() <= 0)
        {
            return  null;
        }
        ServiceInstance serviceInstance = loadBalancer.instances(instances);
        URI uri = serviceInstance.getUri();

        return restTemplate.getForObject(uri+"/payment/lb",String.class);
    }
}
