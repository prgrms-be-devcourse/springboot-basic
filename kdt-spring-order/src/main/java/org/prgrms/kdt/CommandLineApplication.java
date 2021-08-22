package org.prgrms.kdt;

import org.prgrms.kdt.config.AppConfiguration;
import org.prgrms.kdt.customer.controller.CustomerController;
import org.prgrms.kdt.customer.service.CustomerService;
import org.prgrms.kdt.voucher.controller.VoucherController;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CommandLineApplication {
    public static void main(String[] args) {
        // app context에 bean 등록
        var applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(AppConfiguration.class);
        applicationContext.getEnvironment().setActiveProfiles("prod");
        applicationContext.refresh();

        // customer -> black list 불러오기 (컨트롤러 생성자에서 출력하므로 생성만함)
        new CustomerController(applicationContext.getBean(CustomerService.class));

        // Voucher 관리 controller 구동
        VoucherController voucherController = new VoucherController(applicationContext.getBean(VoucherService.class));
        voucherController.run();

        // AppContext 종료
        applicationContext.close();
    }
}
