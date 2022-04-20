package com.blessing333.springbasic.main;

import com.blessing333.springbasic.RunnableService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ServiceProvider {
    private final AnnotationConfigApplicationContext context;
    public RunnableService getRunnableService(ServiceStrategy strategy) {
        switch (strategy){
            case VOUCHER_MANAGING : return context.getBean("voucherManagingService",RunnableService.class);
            case CUSTOMER_MANAGING: return context.getBean("customerApp",RunnableService.class);
            default : throw new NotSupportedStrategyException();
        }
    }
}
