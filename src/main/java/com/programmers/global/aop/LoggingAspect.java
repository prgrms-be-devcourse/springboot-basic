package com.programmers.global.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @AfterThrowing(value = "execution(* com.programmers..*.*(..))", throwing = "ex")
    public void log(JoinPoint joinPoint, Throwable ex) throws Throwable {
        logger.error("에러는 여기서 발생 -> {}", joinPoint.getSignature().getName());
    }

// 로깅을 AOP 적용하여 처리하고 싶었는데, private 메서드에는 동작하지 않는다는 것을 알게 됐습니다.
// private 메서드에도 적용하려면 Spring AOP 방식이 아닌 Assertj 방식의 CTW를 이용해야 한다는 것을
// 알게 되었는데, CTW적용이 잘 이루어지지 않아 실패하였습니다. 로깅 부분에서 너무 많은 시간을 소비해
// PR이 계속 지연되고 있어서 우선은 해당 상태로 PR을 올리도록 하겠습니다.
// +++ 추가
// @AfterThrowing을 통해 예외인 경우에만 로그를 남기고 싶었는데, 이게 구조가 잘못짜여서 에러를 잡지 못하는 것인지
// private메서드 때문인지 이유는 잘 모르겠습니다만 예외가 발생해도 아무런 로그를 남기지 않습니다..
// +++ 추가
// CommandLineApplication의 모든 try catch문을 삭제할 시에는 error 로그가 남겨지긴 합니다만,
// 이렇게 하는 경우 애플리케이션이 멈춰버립니다.

    @Around("execution(* com.programmers..*.*(..))")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        logger.info("Method called with result -> {}, {}", joinPoint.getSignature(), result);
        return result;
    }
}
