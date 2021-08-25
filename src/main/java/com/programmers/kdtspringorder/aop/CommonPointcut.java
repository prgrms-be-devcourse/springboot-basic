package com.programmers.kdtspringorder.aop;

import org.aspectj.lang.annotation.Pointcut;

public class CommonPointcut {

    @Pointcut("execution(public * com.programmers.kdtspringorder..*Service.*(..))")
    public void servicePublicMethodPointcut(){}


    @Pointcut("execution(public * com.programmers.kdtspringorder..*Repository.*(..))")
    public void repositoryMethodPointcut(){}

    @Pointcut("execution(public * com.programmers.kdtspringorder..*Repository.insert(..))")
    public void repositoryInsertMethodPointcut(){}
}
