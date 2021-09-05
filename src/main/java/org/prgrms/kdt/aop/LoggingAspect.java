package org.prgrms.kdt.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("@annotation(org.prgrms.kdt.aop.TrackTime)")
    public Object log(ProceedingJoinPoint jointPoint) throws Throwable {
        log.info("Before method called. {}", jointPoint.getSignature().toString());
        final long startTime = System.nanoTime();
        Object result = jointPoint.proceed();
        final long endTime = System.nanoTime();
        log.info("After method called with result => {} and time taken {} nanoseconds", result, endTime-startTime);
        return result;
    }
}
