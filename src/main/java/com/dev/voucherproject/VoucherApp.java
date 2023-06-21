package com.dev.voucherproject;

import com.dev.voucherproject.config.AppConfiguration;
import com.dev.voucherproject.controller.AppControllerV1;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class VoucherApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfiguration.class);
        AppControllerV1 v1 = ac.getBean(AppControllerV1.class);

        v1.run();
    }
}
