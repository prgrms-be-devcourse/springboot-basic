package com.prgms.VoucherApp.domain.wallet.controller;

import com.prgms.VoucherApp.domain.voucher.model.VoucherService;
import com.prgms.VoucherApp.domain.wallet.model.WalletService;
import com.prgms.VoucherApp.domain.wallet.model.factory.WalletCommandStrategyFactory;
import com.prgms.VoucherApp.domain.wallet.model.strategy.WalletCommandStrategy;
import com.prgms.VoucherApp.view.Input;
import com.prgms.VoucherApp.view.Output;
import com.prgms.VoucherApp.view.WalletCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
public class WalletManagementController implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(WalletManagementController.class);
    private final Input input;
    private final Output output;
    private final VoucherService voucherService;
    private final WalletService walletService;

    public WalletManagementController(Input input, Output output, VoucherService voucherService, WalletService walletService) {
        this.input = input;
        this.output = output;
        this.voucherService = voucherService;
        this.walletService = walletService;
    }

    @Override
    public void run() {
        while (true) {
            try {
                output.printWalletCommand();
                int inputWalletCommandNumber = input.inputWalletCommand();
                WalletCommand walletCommand = WalletCommand.findByCommandNumber(inputWalletCommandNumber);

                if (walletCommand == WalletCommand.EXIT) {
                    return;
                }

                WalletCommandStrategy walletCommandStrategy = WalletCommandStrategyFactory.from(walletCommand);
                walletCommandStrategy.execute(input, output, walletService, voucherService);
            } catch (RuntimeException exception) {
                logger.debug("지갑 관리 프로그램 실행 중에 발생한 예외를 처리하였습니다.", exception);
                output.printErrorMsg(exception.getMessage());
            }
        }
    }
}
