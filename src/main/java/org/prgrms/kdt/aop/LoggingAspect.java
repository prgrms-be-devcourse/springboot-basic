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

    @Pointcut("org.prgrms.kdt.aop.CommonPointcut.repositoryInsertMethodPointCut()")
    public void servicePublicMethodPointcut() {};

    @Around("@annotation(org.prgrms.kdt.aop.TrackTime)")
    public Object log(ProceedingJoinPoint jointPoint) throws Throwable {
        log.info("Before method called. {}", jointPoint.getSignature().toString());
        long startTime = System.nanoTime();
        Object result = jointPoint.proceed();
        long endTime = System.nanoTime() - startTime;
        log.info("After method called with Result => {} and time taken {} nanoseconds", result, endTime);
        return result;
    }

}
