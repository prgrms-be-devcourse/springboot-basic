package com.programmers.springbootbasic;

import com.programmers.springbootbasic.exception.AppExceptionHandler;
import com.programmers.springbootbasic.infrastructure.IO.Console;
import com.programmers.springbootbasic.mediator.RequestProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class VoucherApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(VoucherApplication.class,
            args);

        AppExceptionHandler exceptionHandler = applicationContext.getBean(
            AppExceptionHandler.class);

        while (true) {
            exceptionHandler.handle();
        }
    }

}
