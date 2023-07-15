package com.programmers.springmission.global.aop;

import com.programmers.springmission.global.exception.DuplicateException;
import com.programmers.springmission.global.exception.InvalidInputException;
import com.programmers.springmission.global.exception.NotFoundException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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
        logger.warn("invalidInputException 발생 위치 추적 \n", invalidInputException);
    }

    @AfterThrowing(pointcut = "execution (* com.programmers.springmission..*(..))", throwing = "notFoundException")
    public void notFoundException(JoinPoint joinPoint, NotFoundException notFoundException) {
        logger.warn("NotFoundException 발생 위치 추적 \n", notFoundException);
    }

    @AfterThrowing(pointcut = "execution (* com.programmers.springmission..*(..))", throwing = "duplicateException")
    public void duplicateException(JoinPoint joinPoint, DuplicateException duplicateException) {
        logger.warn("duplicateException 발생 위치 추적 \n", duplicateException);
    }
}
