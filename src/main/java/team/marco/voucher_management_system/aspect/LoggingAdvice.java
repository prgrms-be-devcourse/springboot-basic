package team.marco.voucher_management_system.aspect;

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

    @Around("team.marco.voucher_management_system.aspect.LoggingPointCut.controllerMethodPointcut()")
    public Object controllerMethodLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Execute command {}", joinPoint.getSignature());

        Object result = joinPoint.proceed();

        log.info("End command {}", joinPoint.getSignature());

        return result;
    }

    @Around("team.marco.voucher_management_system.aspect.LoggingPointCut.serviceMethodPointcut()")
    public Object serviceMethodLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Call method {}", joinPoint.getSignature());

        Object result = joinPoint.proceed();

        log.info("Return method {} result -> {}", joinPoint.getSignature(), result);

        return result;
    }

    @Around("team.marco.voucher_management_system.aspect.LoggingPointCut.repositorySavePointcut()")
    public Object repositoryMethodLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.debug("Call method {} args -> {}", joinPoint.getSignature(), joinPoint.getArgs());

        Object result = joinPoint.proceed();

        log.debug("Return method {} result -> {}", joinPoint.getSignature(), result);

        return result;
    }
}
