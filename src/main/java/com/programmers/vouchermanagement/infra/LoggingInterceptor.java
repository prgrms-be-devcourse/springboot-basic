package com.programmers.vouchermanagement.infra;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingInterceptor {
    @Before("@annotation(org.springframework.shell.standard.ShellMethod)")
    public void beforeShellMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        log.info(LoggingUtil.createRunLogMessage(methodName));
    }
}
