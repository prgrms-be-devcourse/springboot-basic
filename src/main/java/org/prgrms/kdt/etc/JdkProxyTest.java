package org.prgrms.kdt.etc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

interface Calculator {
    int add(int a, int b);
}

class CalculatorImpl implements Calculator {

    @Override
    public int add(int a, int b) {
        return a + b;
    }
}

class LoggingInvocationHandler implements InvocationHandler {

    private static final Logger log = LoggerFactory.getLogger(LoggingInvocationHandler.class);

    private final Object target;

    public LoggingInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("{} executed in {}", method.getName(), target.getClass().getCanonicalName());
        return method.invoke(target, args);
    }
}

public class JdkProxyTest {

    private static final Logger log = LoggerFactory.getLogger(LoggingInvocationHandler.class);

    public static void main(String[] args) {
        var calculator = new CalculatorImpl();
        Calculator proxyInstance = (Calculator) Proxy.newProxyInstance(
                LoggingInvocationHandler.class.getClassLoader(),
                new Class[] { Calculator.class },
                new LoggingInvocationHandler(calculator)
        );
        var result = proxyInstance.add(1, 2);
        log.info("Add -> {}", result);
    }
}
