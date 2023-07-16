package kr.co.programmers.springbootbasic.global;

import kr.co.programmers.springbootbasic.voucher.exception.NoValidAmountException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Profile("console")
@ControllerAdvice(annotations = Controller.class)
public class ConsoleGlobalExceptionHandler {
    @ExceptionHandler(NoValidAmountException.class)
    public void handler1() {

    }
}
