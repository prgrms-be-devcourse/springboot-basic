package com.blessing333.springbasic.console_app;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("console")
@RequiredArgsConstructor
public class ControllerMapper {
    private final ApplicationContext context;
    public RunnableController getRunnableController(ServiceStrategy strategy) {
        switch (strategy){
            case VOUCHER_MANAGING : return context.getBean("consoleVoucherController", RunnableController.class);
            case CUSTOMER_MANAGING: return context.getBean("consoleCustomerController", RunnableController.class);
            default : throw new NotSupportedStrategyException();
        }
    }
}
