package com.blessing333.springbasic.main;

import com.blessing333.springbasic.RunnableController;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ControllerMapper {
    private final AnnotationConfigApplicationContext context;
    public RunnableController getRunnableController(ServiceStrategy strategy) {
        switch (strategy){
            case VOUCHER_MANAGING : return context.getBean("voucherController", RunnableController.class);
            case CUSTOMER_MANAGING: return context.getBean("customerController", RunnableController.class);
            default : throw new NotSupportedStrategyException();
        }
    }
}
