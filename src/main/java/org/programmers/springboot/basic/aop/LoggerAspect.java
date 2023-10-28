package org.programmers.springboot.basic.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggerAspect {

    @Before("execution(void org.programmers.springboot.basic..*Controller.*(..)) || " +
            "execution(void org.programmers.springboot.basic..*Repository.*(..)) || " +
            "execution(void org.programmers.springboot.basic..*Mapper.*(..)) || " +
            "execution(void org.programmers.springboot.basic..*Service.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        log.info("'{}' method called in {}", joinPoint.getSignature().getName(), joinPoint.getSignature().getDeclaringTypeName());
    }

    @Around("execution(* org.programmers.springboot.basic..*Controller.*(..)) && !execution(void org.programmers.springboot.basic..*Controller.*(..))")
    public Object logControllerWithResult(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("'{}' method called in {}", joinPoint.getSignature().getName(), joinPoint.getSignature().getDeclaringTypeName());
        Object result = joinPoint.proceed();
        log.info("After '{}' method called with result -> {}", joinPoint.getSignature().getName(), result.toString());
        return result;
    }

    @Around("execution(* org.programmers.springboot.basic..*Repository.*(..)) && !execution(void org.programmers.springboot.basic..*Repository.*(..))")
    public Object logRepositoryWithResult(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("'{}' method called in {}", joinPoint.getSignature().getName(), joinPoint.getSignature().getDeclaringTypeName());
        Object result = joinPoint.proceed();
        log.info("After '{}' method called with result -> {}", joinPoint.getSignature().getName(), result.toString());
        return result;
    }

    @Around("execution(* org.programmers.springboot.basic..*Service.*(..)) && !execution(void org.programmers.springboot.basic..*Service.*(..))")
    public Object logServiceWithResult(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("'{}' method called in {}", joinPoint.getSignature().getName(), joinPoint.getSignature().getDeclaringTypeName());
        Object result = joinPoint.proceed();
        log.info("After '{}' method called with result -> {}", joinPoint.getSignature().getName(), result.toString());
        return result;
    }

    @Around("execution(* org.programmers.springboot.basic..*Mapper.*(..)) && !execution(void org.programmers.springboot.basic..*Mapper.*(..))")
    public Object logMapperWithResult(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("'{}' method called in {}", joinPoint.getSignature().getName(), joinPoint.getSignature().getDeclaringTypeName());
        Object result = joinPoint.proceed();
        log.info("After '{}' method called with result -> {}", joinPoint.getSignature().getName(), result.toString());
        return result;
    }

    @Pointcut("execution(* org.programmers.springboot.basic..*.*(..))")
    public void allMethods() {}

    @AfterThrowing(pointcut = "allMethods()", throwing = "runtimeException")
    public void logException(RuntimeException runtimeException) {
        log.warn(runtimeException.getMessage());
    }
}
