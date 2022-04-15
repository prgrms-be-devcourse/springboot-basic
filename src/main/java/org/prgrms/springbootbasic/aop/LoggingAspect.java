package org.prgrms.springbootbasic.aop;

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

    @Pointcut("execution(* org.prgrms.springbootbasic..*(..))")
    public void allVoucherApplicationMethodPointcut() {
    }


    @Around("org.prgrms.springbootbasic.aop.LoggingAspect.allVoucherApplicationMethodPointcut()")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Before method called. {}", joinPoint.getSignature().toString());
        var result = joinPoint.proceed();
        logger.info("After method called. {}", joinPoint.getSignature().toString());
        return result;
    }
}
