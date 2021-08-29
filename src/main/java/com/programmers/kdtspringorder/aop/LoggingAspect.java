package com.programmers.kdtspringorder.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(public * com.programmers.kdtspringorder..*.*(..))")
    public void servicePublicMethodPointcut(){}

    @Around("com.programmers.kdtspringorder.aop.CommonPointcut.repositoryMethodPointcut()")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
//        logger.info("Before method called. {}", joinPoint.getSignature().toString());
        var result = joinPoint.proceed();
//        logger.info("After method called. with result => {}", result);
        return result;
    }

    @Around("@annotation(com.programmers.kdtspringorder.aop.TrackTime)")
    public Object trackTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.nanoTime();
        var result = joinPoint.proceed();
        long end = System.nanoTime();
//        logger.info("After method called time taken {} ", end - start);
        return result;
    }
}