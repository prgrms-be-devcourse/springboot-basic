package programmers.org.kdt.aop;

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
    private  static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

//    @Pointcut("execution(public * programmers.org.kdt..*.*(..))")
//    public void servicePublicMethodPointcut() {}

    //@Around("execution(public * programmers.org.kdt..*.*(..))")
    //@Around("programmers.org.kdt.aop.CommonPointcut.repositoryInsertMethodPointcut()")
    @Around("@annotation(programmers.org.kdt.aop.TrackTime)")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Before method called. {}", joinPoint.getSignature().toString());
        var startTime= System.nanoTime();
        var result = joinPoint.proceed();
        var endTime = System.nanoTime() - startTime;
        log.info("After method called with result. {} and time taken {} nanoseconds", result, endTime);
        return result;
    }

}
