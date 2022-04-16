package org.prgrms.kdtspringvoucher.aop;

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

    // point cut: 비즈니스 로직을 가지고 타겟 오브젝트에 여러 join point 중에서 advice를 어떻게 적용시킬지 aop게 알려주는 하나의 키워드
    // execution을 할 때 적용하겠다는 의미
    // @Around("execution(public * )") -> public method가 execution 할 때 어디바이즈를 Around로 적용하겠다는 말.
//    @Around("org.prgrms.kdtspringvoucher.aop.CommonPointcut.servicePublicMethodPointcut()")
    @Around("@annotation(org.prgrms.kdtspringvoucher.aop.TrackTime)")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Before method called. {} ", joinPoint.getSignature().toString());
        var startTime = System.nanoTime();
        var result = joinPoint.proceed();
        var endTime = System.nanoTime() - startTime;
        log.info("After method called with result -> {} and timetaken {} nanoseconds", result, endTime);
        return result;
    }
}
