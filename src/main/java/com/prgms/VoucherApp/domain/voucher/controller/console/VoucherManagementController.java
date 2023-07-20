package com.prgms.VoucherApp.domain.voucher.controller.console;


import com.prgms.VoucherApp.domain.voucher.model.VoucherService;
import com.prgms.VoucherApp.domain.voucher.model.factory.VoucherCommandStrategyFactory;
import com.prgms.VoucherApp.domain.voucher.model.strategy.VoucherCommandStrategy;
import com.prgms.VoucherApp.view.Input;
import com.prgms.VoucherApp.view.Output;
import com.prgms.VoucherApp.view.VoucherCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
public class VoucherManagementController implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(VoucherManagementController.class);
    private final VoucherService voucherService;
    private final Input input;
    private final Output output;

    public VoucherManagementController(VoucherService voucherService, Input input, Output output) {
        this.voucherService = voucherService;
        this.input = input;
        this.output = output;
    }

    @Override
    public void run() {
        while (true) {
            try {
                output.printVoucherCommand();
                int inputCommandNumber = input.inputVoucherCommand();
                VoucherCommand command = VoucherCommand.findByVoucherCommandNumber(inputCommandNumber);

                if (command == VoucherCommand.EXIT) {
                    return;
                }

                VoucherCommandStrategy voucherCommandStrategy = VoucherCommandStrategyFactory.from(command);
                voucherCommandStrategy.execute(input, output, voucherService);
            } catch (RuntimeException exception) {
                logger.debug("할인권 관리 프로그램 실행 중에 발생한 예외를 처리하였습니다.", exception);
                output.printErrorMsg(exception.getMessage());
            }
        }
    }
}
