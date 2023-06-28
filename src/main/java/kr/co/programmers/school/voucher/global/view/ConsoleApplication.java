package kr.co.programmers.school.voucher.global.view;

import kr.co.programmers.school.voucher.domain.voucher.controller.VoucherController;
import kr.co.programmers.school.voucher.domain.voucher.dto.VoucherListResponse;
import kr.co.programmers.school.voucher.domain.voucher.dto.VoucherRequest;
import kr.co.programmers.school.voucher.domain.voucher.enums.Command;
import kr.co.programmers.school.voucher.domain.voucher.enums.VoucherType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ConsoleApplication implements CommandLineRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsoleApplication.class);

    private final InputView inputView;
    private final OutputView outputView;
    private final VoucherController voucherController;

    public ConsoleApplication(InputView inputView, OutputView outputView, VoucherController voucherController) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.voucherController = voucherController;
    }

    @Override
    public void run(String... args) {
        try {
            Command command;
            do {
                outputView.printCommand();
                String input = inputView.input();
                command = Command.from(input);
                processCommand(command);
            }
            while (isContinue(command));
        }
        catch (Exception exception) {
            LOGGER.error("[Console Application] {}", exception.getMessage());
            throw exception;
        }
    }

    private void processCommand(Command command) {
        if (command.isCreation()) {
            outputView.printVoucherType();

            String input = inputView.input();
            VoucherType voucherType = VoucherType.from(input);
            printVoucherMessage(voucherType);

            String amount = inputView.input();
            VoucherRequest voucherRequest = VoucherRequest.of(amount, voucherType);

            voucherController.createVoucher(voucherRequest);
            outputView.printSuccessMessage();
        }
        else if (command.isInquiry()) {
            VoucherListResponse voucherListResponse = voucherController.getAllVouchers();
            outputView.printVoucherList(voucherListResponse);
        }
    }

    private boolean isContinue(Command command) {
        if (command.isExit()) {
            return false;
        }

        return true;
    }

    private void printVoucherMessage(VoucherType voucherType) {
        if (voucherType.isFixedAmount()) {
            outputView.printFixedDiscountVoucherMessage();
            return;
        }

        outputView.printPercentDiscountVoucherMessage();
    }
}