package com.programmers.vouchermanagement.global.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
public class ControllerLogAop {

    @Before("bean(*Controller)")
    public void beforeParameterLog(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        log.info("### method name: {}", method.getName());

        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            log.info("### parameter type: {}", arg.getClass().getSimpleName());
            log.info("### parameter value: {}", arg);
        }
    }
}
