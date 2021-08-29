package org.prgrms.kdt.aop;

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

    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    // advice
    @Around("@annotation(org.prgrms.kdt.aop.TrackTime)") // point cut
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Before method called. {}", joinPoint.getSignature().toString());
        var startTime = System.nanoTime();
        var result = joinPoint.proceed();
        var endTime = System.nanoTime() - startTime;
        log.info("After method called with result => {} and time taken by {} nanoseconds", result, endTime);
        return result;
    }
}
