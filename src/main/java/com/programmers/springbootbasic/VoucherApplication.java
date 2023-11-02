package com.programmers.springbootbasic;

import com.programmers.springbootbasic.exception.AppExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class VoucherApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(
            VoucherApplication.class, args
        );

        startAppWithErrorHandler(applicationContext);
    }

    private static void startAppWithErrorHandler(
        ConfigurableApplicationContext applicationContext
    ) {
        AppExceptionHandler exceptionHandler = applicationContext.getBean(
            AppExceptionHandler.class);
        do {
            exceptionHandler.handle();
        } while (applicationContext.isActive());
    }
}
