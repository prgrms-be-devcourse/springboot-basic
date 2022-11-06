package com.example.springbootbasic;

import com.example.springbootbasic.config.ApplicationConfig;
import com.example.springbootbasic.console.ConsoleStatus;
import com.example.springbootbasic.controller.MainController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import static com.example.springbootbasic.console.ConsoleStatus.END;
import static com.example.springbootbasic.console.ConsoleStatus.SUCCESS;

@ComponentScan
public class VoucherConsoleApplication {

    public static void main(String[] args) {
        ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        MainController mainController = ac.getBean(MainController.class);

        ConsoleStatus status = SUCCESS;
        while (status != END) {
            status = mainController.executeVoucherProgram().getStatus();
        }
    }
}

