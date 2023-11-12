package com.programmers.vouchermanagement.infra.aspect;

import com.programmers.vouchermanagement.infra.io.ConsoleOutput;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PrintHelpAspect {
    @AfterReturning("@annotation(org.springframework.shell.standard.ShellMethod)")
    public void afterControllerMethod() {
        ConsoleOutput.printHelp();
    }
}
