package org.programmers;

import org.programmers.console.Console;
import org.programmers.customer.service.CustomerService;
import org.programmers.voucher.repository.VoucherRepository;
import org.programmers.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CommandLineApplication {

//    public static void main(String[] args){
//        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
//        var voucherService = applicationContext.getBean(VoucherService.class);
//        var customerService = applicationContext.getBean(CustomerService.class);
//
//        try {
//            new VoucherProgram(voucherService,new Console(),customerService).run();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
