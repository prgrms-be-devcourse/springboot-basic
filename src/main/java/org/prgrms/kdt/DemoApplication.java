package org.prgrms.kdt;

import org.prgrms.kdt.io.Input;
import org.prgrms.kdt.io.Output;
import org.prgrms.kdt.model.VoucherProgram;
import org.prgrms.kdt.service.VoucherService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        var applicationContext = SpringApplication.run(DemoApplication.class, args);
        Input input = new Input();
        Output output = new Output();
        VoucherService voucherService = applicationContext.getBean(VoucherService.class);

        new VoucherProgram(input, output, voucherService).run();
    }
}
