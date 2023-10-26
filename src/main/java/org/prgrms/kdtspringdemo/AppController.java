package org.prgrms.kdtspringdemo;

import org.prgrms.kdtspringdemo.customer.controller.CustomerController;
import org.prgrms.kdtspringdemo.view.InputConsole;
import org.prgrms.kdtspringdemo.view.OutputConsole;
import org.prgrms.kdtspringdemo.voucher.VoucherFunction;
import org.prgrms.kdtspringdemo.voucher.controller.VoucherController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class AppController implements CommandLineRunner {
    private final InputConsole inputConsole;
    private final OutputConsole outputConsole;
    private final VoucherController voucherController;
    private final CustomerController customerController;
    private final Logger logger = LoggerFactory.getLogger(KdtSpringDemoApplication.class);

    public AppController(InputConsole inputConsole, OutputConsole outputConsole, VoucherController voucherController, CustomerController customerController) {
        this.inputConsole = inputConsole;
        this.outputConsole = outputConsole;
        this.voucherController = voucherController;
        this.customerController = customerController;
    }

    @Override
    public void run(String... args) throws IOException {
        try {
            while (true) {
                outputConsole.start();
                String fun = inputConsole.getString();
                try {
                    VoucherFunction.findByCode(fun).execute(voucherController);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
