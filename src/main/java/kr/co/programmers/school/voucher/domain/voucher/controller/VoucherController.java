package kr.co.programmers.school.voucher.domain.voucher.controller;

import kr.co.programmers.school.voucher.domain.voucher.enums.Command;
import kr.co.programmers.school.voucher.domain.voucher.dto.VoucherRequest;
import kr.co.programmers.school.voucher.domain.voucher.enums.VoucherType;
import kr.co.programmers.school.voucher.domain.voucher.service.VoucherService;
import kr.co.programmers.school.voucher.view.InputView;
import kr.co.programmers.school.voucher.view.OutputView;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;


@Controller
public class VoucherController implements CommandLineRunner {
    private final InputView inputView;
    private final OutputView outputView;
    private final VoucherService voucherService;

    public VoucherController(InputView inputView, OutputView outputView, VoucherService voucherService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.voucherService = voucherService;
    }

    @Override
    public void run(String... args) throws Exception {
        Command command;
        do {
            outputView.printCommand();
            String input = inputView.input();
            System.out.println();
            command = Command.from(input);
            processCommand(command);
        }
        while (isContinue(command));
    }

    private void processCommand(Command command) {
        if (command.isCreation()) {
            outputView.printVoucherType();

            String input = inputView.input();
            VoucherType voucherType = VoucherType.from(input);
            printVoucherMessage(voucherType);

            int amount = inputView.inputInt();
            VoucherRequest voucherRequest = VoucherRequest.of(amount, voucherType);

            voucherService.createVoucher(voucherRequest);
        }
        else if (command.isInquiry()) {
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