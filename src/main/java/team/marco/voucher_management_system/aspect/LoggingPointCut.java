package team.marco.voucher_management_system.aspect;

import org.aspectj.lang.annotation.Pointcut;

public final class LoggingPointCut {
    private LoggingPointCut() {
    }

    @Pointcut("execution(* team.marco.voucher_management_system.controller..*(..))")
    public static void controllerMethodPointcut() {
    }

    @Pointcut("execution(* team.marco.voucher_management_system.service..*(..))")
    public static void serviceMethodPointcut() {
    }

    @Pointcut("execution(* team.marco.voucher_management_system.repository..*(..))")
    public static void repositorySavePointcut() {
    }
}
