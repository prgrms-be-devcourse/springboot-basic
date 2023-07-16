package kr.co.programmers.springbootbasic.global;

import kr.co.programmers.springbootbasic.voucher.exception.NoValidAmountException;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Profile("web")
@RestControllerAdvice(annotations = RestController.class)
public class WebGlobalExceptionHandler {
    @ExceptionHandler(NoValidAmountException.class)
    public void handler1() {

    }
}
