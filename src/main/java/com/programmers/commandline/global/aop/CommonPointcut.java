package com.programmers.commandline.global.aop;

import org.aspectj.lang.annotation.Pointcut;

public class CommonPointcut {
    @Pointcut("execution(public * com.programmers.commandline..*.*(..))")
    public void packagePublicMethodPointcut() {}
}
