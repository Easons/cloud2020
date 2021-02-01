package com.pangpan.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author pangpan
 * @date 2021-01-14
 *
 * 1:先获得机器的列表
 */
@Component
public class MyLoadBalancer implements  LoadBalancer {

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    public  final int getAndIncrement(){
        int current;//当前的值
        int next;//下一个值

        do{
            current = this.atomicInteger.get();
            next = current >= 2147483647 ? 0:current + 1;//判断如果是最大值了就从头开始
        }while(this.atomicInteger.compareAndSet(current,next));

        System.out.println("******next:"+next);
        return  next;
    }


    @Override
    public ServiceInstance instances(List<ServiceInstance> serviceInstances) {

        int index = getAndIncrement() % serviceInstances.size();//取模，看是取什么值



        return null;
    }
}
