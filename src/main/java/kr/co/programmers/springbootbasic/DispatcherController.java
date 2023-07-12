package kr.co.programmers.springbootbasic;

import kr.co.programmers.springbootbasic.customer.controller.CustomerController;
import kr.co.programmers.springbootbasic.customer.domain.CustomerStatus;
import kr.co.programmers.springbootbasic.customer.dto.CustomerResponse;
import kr.co.programmers.springbootbasic.io.Input;
import kr.co.programmers.springbootbasic.io.Output;

import kr.co.programmers.springbootbasic.io.enums.CustomerFindCommand;
import kr.co.programmers.springbootbasic.io.enums.CustomerServiceCommand;
import kr.co.programmers.springbootbasic.io.enums.EntireServiceCommand;
import kr.co.programmers.springbootbasic.io.enums.VoucherServiceCommand;
import kr.co.programmers.springbootbasic.io.enums.WalletServiceCommand;
import kr.co.programmers.springbootbasic.voucher.controller.ConsoleVoucherController;
import kr.co.programmers.springbootbasic.voucher.domain.VoucherType;
import kr.co.programmers.springbootbasic.voucher.dto.VoucherResponse;
import kr.co.programmers.springbootbasic.wallet.controller.ConsoleWalletController;
import kr.co.programmers.springbootbasic.wallet.dto.WalletResponse;
import kr.co.programmers.springbootbasic.wallet.dto.WalletSaveDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
@Profile("console")
public class DispatcherController implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherController.class);
    private final Input inputConsole;
    private final Output outputConsole;
    private final ConsoleVoucherController voucherController;
    private final CustomerController customerController;
    private final ConsoleWalletController walletController;
    private boolean isExit;

    public DispatcherController(Input inputConsole, Output outputConsole, ConsoleVoucherController voucherController, CustomerController customerController, ConsoleWalletController walletController) {
        this.inputConsole = inputConsole;
        this.outputConsole = outputConsole;
        this.voucherController = voucherController;
        this.customerController = customerController;
        this.walletController = walletController;
    }

    @Override
    public void run(ApplicationArguments args) {
        while (!isExit) {
            executeServiceLoop();
        }
        logger.info("서비스를 종료합니다.");
        outputConsole.printExit();
    }

    private void executeServiceLoop() {
        try {
            doService();
        } catch (RuntimeException e) {
            outputConsole.printMessage(e.getMessage());
        }
    }

    private void doService() {
        outputConsole.printProgramMenu();
        EntireServiceCommand command = inputConsole.readEntireServiceCommand();

        switch (command) {
            case EXIT -> isExit = true;
            case VOUCHER_SERVICE -> voucherController.doVoucherService();
            case CUSTOMER_SERVICE -> customerController.doCustomerService();
            case WALLET_SERVICE -> walletController.doWalletService();
        }
    }
}
