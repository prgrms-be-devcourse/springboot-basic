package com.programmers.application.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeMeasurementLoggingAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(TimeMeasurementLoggingAspect.class);

    @Around("execution(public * com.programmers.application.service..*.*(..))")
    public Object measureTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        String methodName = joinPoint.getSignature().getName();
        LOGGER.info("method name: {}", methodName);
        LOGGER.info("method execution time: {}", executionTime);
        return result;
    }
}
