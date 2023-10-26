package team.marco.vouchermanagementsystem.aspect;

import org.aspectj.lang.annotation.Pointcut;

public final class LoggingPointCut {
    private LoggingPointCut() {
    }

    @Pointcut("execution(* team.marco.vouchermanagementsystem.service..*(..))")
    public static void serviceMethodPointcut() {
    }

    @Pointcut("execution(* team.marco.vouchermanagementsystem.repository..save(..))")
    public static void repositorySavePointcut() {
    }
}
