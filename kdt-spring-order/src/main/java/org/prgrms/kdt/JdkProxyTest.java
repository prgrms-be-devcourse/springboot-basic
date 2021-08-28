package org.prgrms.kdt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


class CalculatorImpl implements Calculator {

    @Override
    public int add(int a, int b) {
        return a + b;
    }
}

interface Calculator {
    int add (int a, int b);
}

class LoggingInvocationHandler implements InvocationHandler {
    private static final Logger logger = LoggerFactory.getLogger(LoggingInvocationHandler.class);

    private final Object target;

    public LoggingInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        logger.info("{} executed", method.getName());
        return method.invoke(target, args);
    }
}

public class JdkProxyTest {
    private static final Logger logger = LoggerFactory.getLogger(JdkProxyTest.class);

    public static void main(String[] args) {
        CalculatorImpl calculator = new CalculatorImpl();
        Calculator proxyCalculator = (Calculator) Proxy.newProxyInstance(LoggingInvocationHandler.class.getClassLoader(),
                new Class[]{Calculator.class},
                new LoggingInvocationHandler(calculator));

        int res = proxyCalculator.add(1, 2);
        logger.info("Add -> {}", res);

    }
}
