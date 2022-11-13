package com.programmers.commandline.global.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Around("com.programmers.commandline.global.aop.CommonPointcut.packagePublicMethodPointcut()")
    public Object logging(ProceedingJoinPoint pjp) throws Throwable {
        long beforeTimeMillis = System.nanoTime();

        logger.info("실행 시작 : "
                + pjp.getSignature().getDeclaringTypeName() + "."
                + pjp.getSignature().getName());

        Object result = pjp.proceed();

        long afterTimeMillis = System.nanoTime() - beforeTimeMillis;

        logger.info("실행 완료 : "
                + afterTimeMillis + "ns 소요 : "
                + pjp.getSignature().getDeclaringTypeName()
                + " : "
                + pjp.getSignature().getName());

        return result;
    }

    public static Logger getLogger() {
        AnsiOutput.setEnabled(AnsiOutput.Enabled.ALWAYS);

        return logger;
    }
}