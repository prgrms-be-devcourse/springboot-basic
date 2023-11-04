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

    private static void logMethodCall(JoinPoint joinPoint) {
        log.warn("'{}' method called in '{}'",
                joinPoint.getSignature().getName(),
                joinPoint.getSignature().getDeclaringTypeName());
    }

    @Pointcut("execution(void org.programmers.springboot.basic..*Controller.*(..)) || " +
            "execution(void org.programmers.springboot.basic..*Repository.*(..)) || " +
            "execution(void org.programmers.springboot.basic..*Mapper.*(..)) || " +
            "execution(void org.programmers.springboot.basic..*Service.*(..))")
    public void voidMethods() {}

    @Pointcut("execution(* org.programmers.springboot.basic..*Controller.*(..)) && !voidMethods() ||" +
            "execution(* org.programmers.springboot.basic..*Repository.*(..)) && ! voidMethods() || " +
            "execution(* org.programmers.springboot.basic..*Service.*(..)) && ! voidMethods() || " +
            "execution(* org.programmers.springboot.basic..*Mapper.*(..)) && ! voidMethods()")
    public void nonVoidMethods() {}

    @Pointcut("execution(* org.programmers.springboot.basic..*.*(..))")
    public void allMethods() {}

    @Before("voidMethods()")
    public void logBefore(JoinPoint joinPoint) {
        logMethodCall(joinPoint);
    }

    @Around("nonVoidMethods()")
    public Object logAroundWithResult(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("'{}' method called in {}",
                joinPoint.getSignature().getName(),
                joinPoint.getSignature().getDeclaringTypeName());
        Object result = joinPoint.proceed();
        log.info("After '{}' method called with result '{}'",
                joinPoint.getSignature().getName(),
                result != null ? result.toString() : "null");
        return result;
    }

    @AfterThrowing(pointcut = "allMethods()", throwing = "runtimeException")
    public void logException(RuntimeException runtimeException) {
        log.warn(runtimeException.getMessage());
    }
}
