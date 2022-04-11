package org.prgms.kdtspringvoucher;

import org.prgms.kdtspringvoucher.blackList.service.BlackListService;
import org.prgms.kdtspringvoucher.io.Input;
import org.prgms.kdtspringvoucher.io.Output;
import org.prgms.kdtspringvoucher.voucher.service.VoucherService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class KdtSpringVoucherApplication {

    public static void main(String[] args) {

        SpringApplication springApplication = new SpringApplication(KdtSpringVoucherApplication.class);
        springApplication.setAdditionalProfiles("prod");
        ApplicationContext applicationContext = springApplication.run(args);

        VoucherService voucherService = applicationContext.getBean(VoucherService.class);
        Input input = applicationContext.getBean(Input.class);
        Output output = applicationContext.getBean(Output.class);
        BlackListService blackListService = applicationContext.getBean(BlackListService.class);

        new CommandLineApplication(input, output, voucherService, blackListService).run();
    }

}
