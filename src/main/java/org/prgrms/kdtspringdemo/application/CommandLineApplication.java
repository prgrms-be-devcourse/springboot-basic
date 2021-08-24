package org.prgrms.kdtspringdemo.application;

import org.prgrms.kdtspringdemo.application.ConsoleApp;
import org.prgrms.kdtspringdemo.configuration.AppConfiguration;
import org.prgrms.kdtspringdemo.console.Console;
import org.prgrms.kdtspringdemo.console.CustomerOperator;
import org.prgrms.kdtspringdemo.console.VoucherOperator;
import org.prgrms.kdtspringdemo.voucher.VoucherRepository;
import org.prgrms.kdtspringdemo.voucher.VoucherService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CommandLineApplication{
    public static void main(String[] args) {
        var application = new AnnotationConfigApplicationContext();
        application.register(AppConfiguration.class);
        application.refresh();
        VoucherService voucherService = application.getBean(VoucherService.class);

        System.out.println("#####" + application.getBean(VoucherRepository.class).getClass().getCanonicalName());
        new ConsoleApp(new Console(), new VoucherOperator(voucherService), new CustomerOperator());
    }
}
