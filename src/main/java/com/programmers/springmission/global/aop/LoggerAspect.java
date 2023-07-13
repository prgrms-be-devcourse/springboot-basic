package com.programmers.springmission.global.aop;

import com.programmers.springmission.global.exception.InvalidInputException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
public class LoggerAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggerAspect.class);

    @Around("execution(* com.programmers.springmission..*Controller.*(..))")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {

        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        long startTime = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long finishTime = System.currentTimeMillis();

        double invokeTime = (finishTime - startTime) / 1000.0;

        logger.info("invoke method : {} - {} - / timeTaken : {} s", className, methodName, invokeTime);
        return proceed;
    }

    @AfterThrowing(pointcut = "execution (* com.programmers.springmission..*(..))", throwing = "invalidInputException")
    public void invalidInputExceptionThrowing(JoinPoint joinPoint, InvalidInputException invalidInputException) {

        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        String threadName = Thread.currentThread().getName();

        LocalDateTime invokeTime = LocalDateTime.now();

        logger.warn("throw method : {} - {} - / current thread : {} / timeTaken : {} s", className, methodName, threadName, invokeTime, invalidInputException);
    }
}
