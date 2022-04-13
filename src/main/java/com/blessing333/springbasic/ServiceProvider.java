package com.blessing333.springbasic;

import com.blessing333.springbasic.exception.NotSupportedStrategyException;
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
            default : throw new NotSupportedStrategyException();
        }
    }
}
