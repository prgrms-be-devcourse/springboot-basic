package team.marco.vouchermanagementsystem.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAdvice {
    public static final Logger log = LoggerFactory.getLogger(LoggingAdvice.class);

    @Around("team.marco.vouchermanagementsystem.aspect.LoggingPointCut.serviceMethodPointcut()")
    public Object serviceMethodLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Call method {}", joinPoint.getSignature());

        Object result = joinPoint.proceed();

        log.info("Return method {} result -> {}", joinPoint.getSignature(), result);

        return result;
    }

    @Around("team.marco.vouchermanagementsystem.aspect.LoggingPointCut.repositorySavePointcut()")
    public Object repositoryMethodLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.debug("Call save {} args -> {}", joinPoint.getSignature(), joinPoint.getArgs());

        Object result = joinPoint.proceed();

        log.debug("Return save {}", joinPoint.getSignature());

        return result;
    }
}
