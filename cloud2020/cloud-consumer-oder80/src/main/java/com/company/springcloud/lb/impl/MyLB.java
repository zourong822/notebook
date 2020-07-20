package com.company.springcloud.lb.impl;

import com.company.springcloud.lb.LoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
public class MyLB implements LoadBalancer {
    private AtomicInteger atomicInteger=new AtomicInteger(0);

    public int getAddIncrement(){
        int current;
        int next;
        do{
            current=atomicInteger.get();
            next=current>=2147483647?0:current+1;
        }while (!atomicInteger.compareAndSet(current,next));
        log.info("第几次访问，次数：{}",next);
        return next;
    }
    @Override
    public ServiceInstance getInstance(List<ServiceInstance> instances) {
        return instances.get(getAddIncrement()%instances.size());
    }
}
