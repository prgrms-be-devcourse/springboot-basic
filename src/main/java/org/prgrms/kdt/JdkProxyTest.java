package org.prgrms.kdt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


class CalculatorImpl implements Calculator {

    @Override
    public int add(int a, int b) {
        return 0;
    }
}

interface Calculator {
    int add(int a, int b);
}

class LogginInvocationHandler implements InvocationHandler {

    private static final Logger log = LoggerFactory.getLogger(LogginInvocationHandler.class);

    private final Object target;

    public LogginInvocationHandler(Object target) {
        this.target = target;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("{} executed in {}", method.getName(), target.getClass().getCanonicalName());
        return method.invoke(target, args);
    }
}

public class JdkProxyTest {

    private static final Logger log = LoggerFactory.getLogger(JdkProxyTest.class);

    public static void main(String[] args) {

        CalculatorImpl calculator = new CalculatorImpl();
        Calculator proxyInstance = (Calculator) Proxy.newProxyInstance(LogginInvocationHandler.class.getClassLoader(),
            new Class[]{Calculator.class},
            new LogginInvocationHandler(calculator));
        int result = proxyInstance.add(1, 2);
        log.info("Add -> {}", result);
    }
}
