package com.programmers.springbootbasic;

import com.programmers.springbootbasic.exception.AppExceptionHandler;
import com.programmers.springbootbasic.infrastructure.IO.Console;
import com.programmers.springbootbasic.mediator.RequestProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class VoucherApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(
            VoucherApplication.class,
            args);

        AppExceptionHandler exceptionHandler = applicationContext.getBean(
            AppExceptionHandler.class);

        // 컨텍스트가 종료된 경우
        do {
            exceptionHandler.handle();
        } while (applicationContext.isActive());
    }

}
