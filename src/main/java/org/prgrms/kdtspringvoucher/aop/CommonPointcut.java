package org.prgrms.kdtspringvoucher.aop;

import org.aspectj.lang.annotation.Pointcut;

public class CommonPointcut {


    @Pointcut("execution(public * org.prgrms.kdtspringvoucher..service.*.*(..))")
    public void servicePublicMethodPointcut() {};

    @Pointcut("execution(* org.prgrms.kdtspringvoucher..*Repository.insert.*(..))")
    public void repositoryMethodPointcut() {};

}
