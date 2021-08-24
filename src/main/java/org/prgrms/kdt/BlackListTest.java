package org.prgrms.kdt;

import org.prgrms.kdt.customer.CustomerService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BlackListTest {
    public static void main(String[] args) {
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);

        var customerService = applicationContext.getBean(CustomerService.class);

        var blackList = customerService.getBlackList();
        for(var black : blackList){
            System.out.println(black);
        }
    }
}
