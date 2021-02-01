package com.pangpan;

import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author pangpan
 * @date 2021-01-17
 */
public class TestFacrotyBean implements FactoryBean {

    @Override
    public Object getObject() throws Exception {
        Proxy.newProxyInstance(TestFacrotyBean.class.getClassLoader(), null, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return null;
            }
        });
        return null;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }
}
