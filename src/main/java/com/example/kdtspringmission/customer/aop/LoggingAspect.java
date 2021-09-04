package com.example.kdtspringmission.customer.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

//@Aspect
//@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

//    @Around("execution(public * com.example.kdtspringmission..*Repository.*())")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("before method call");
        Object result = joinPoint.proceed();
        logger.info("after method call with result -> {}", result);
        return result;
    }
}
