package com.programmers.kdtspringorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdpProxyTest {


    public static void main(String[] args) {
        CalculatorImpl calculator = new CalculatorImpl();
        Calculator proxyInstance = (Calculator) Proxy.newProxyInstance(
                LoggingInvocationHandler.class.getClassLoader(),
                new Class[]{Calculator.class},
                new LoggingInvocationHandler(calculator));

        proxyInstance.add(1,2);

    }
}


class CalculatorImpl implements Calculator {
    @Override
    public int add(int a, int b) {
        return 0;
    }
}


interface Calculator{
    int add(int a, int b);
}

class LoggingInvocationHandler implements InvocationHandler{

    private static final Logger logger = LoggerFactory.getLogger(LoggingInvocationHandler.class);
    private final Object target;

    public LoggingInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        logger.info("{} executed in {}", method.getName(), target.getClass().getCanonicalName());
        return method.invoke(target,args);
    }
}