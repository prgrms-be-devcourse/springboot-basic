package org.prgrms.kdtspringdemo;

import org.prgrms.kdtspringdemo.customer.CustomerFunction;
import org.prgrms.kdtspringdemo.customer.controller.CustomerController;
import org.prgrms.kdtspringdemo.view.InputConsole;
import org.prgrms.kdtspringdemo.view.OutputConsole;
import org.prgrms.kdtspringdemo.voucher.VoucherFunction;
import org.prgrms.kdtspringdemo.voucher.controller.VoucherController;
import org.prgrms.kdtspringdemo.wallet.WalletFunction;
import org.prgrms.kdtspringdemo.wallet.controller.WalletController;
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
    private final WalletController walletController;
    private final Logger logger = LoggerFactory.getLogger(KdtSpringDemoApplication.class);

    public AppController(InputConsole inputConsole, OutputConsole outputConsole, VoucherController voucherController, CustomerController customerController, WalletController walletController) {
        this.inputConsole = inputConsole;
        this.outputConsole = outputConsole;
        this.voucherController = voucherController;
        this.customerController = customerController;
        this.walletController = walletController;
    }

    @Override
    public void run(String... args) throws IOException {
        try {
            while (true) {
                outputConsole.startProgram();
                String fun = inputConsole.getString();
                try {
                    // Mode 선택
                    ProgramFunction mode = ProgramFunction.findByCode(fun);
                    mode.execute(outputConsole);
                    switch (mode) {
                        case VOUCHER:
                            fun = inputConsole.getString();  //voucher 실행
                            VoucherFunction.findByCode(fun).execute(voucherController);
                            break;
                        case CUSTOMER:
                            fun = inputConsole.getString();
                            CustomerFunction.findByCode(fun).execute(customerController);
                            break;
                        case WALLET:
                            fun = inputConsole.getString();
                            WalletFunction.findByCode(fun).execute(walletController);
                            break;
                        case EXIT:
                            ProgramFunction.findByCode(fun).execute(outputConsole);
                            System.exit(0);
                            break;

                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
