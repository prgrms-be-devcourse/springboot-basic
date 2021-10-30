package programmers.org.kdt.aop;

import org.aspectj.lang.annotation.Pointcut;

public class CommonPointcut {

    @Pointcut("execution(public * programmers.org.kdt..*.*(..))")
    public void servicePublicMethodPointcut() {}

    @Pointcut("execution(* programmers.org.kdt..*Repository.*(..))")
    public void repositoryMethodPointcut() {}

    @Pointcut("execution(* programmers.org.kdt..*Repository.insert(..))")
    public void repositoryInsertMethodPointcut() {}
}
