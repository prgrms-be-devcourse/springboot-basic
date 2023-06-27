package kr.co.programmers.school.voucher.domain.voucher.controller;

import kr.co.programmers.school.voucher.domain.voucher.dto.VoucherListResponse;
import kr.co.programmers.school.voucher.domain.voucher.enums.Command;
import kr.co.programmers.school.voucher.domain.voucher.dto.VoucherRequest;
import kr.co.programmers.school.voucher.domain.voucher.enums.VoucherType;
import kr.co.programmers.school.voucher.domain.voucher.service.VoucherService;
import kr.co.programmers.school.voucher.view.InputView;
import kr.co.programmers.school.voucher.view.OutputView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.util.regex.Pattern;

@Controller
public class VoucherController implements CommandLineRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(VoucherController.class);

    private final InputView inputView;
    private final OutputView outputView;
    private final VoucherService voucherService;

    private static final Pattern PERCENT_INPUT_RANGE_PATTERN = Pattern.compile("^[1-9][0-9]?$|^100$");
    private static final Pattern FIXED_INPUT_PATTERN = Pattern.compile("^[1-9]\\d*$");

    public VoucherController(InputView inputView, OutputView outputView, VoucherService voucherService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.voucherService = voucherService;
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
        catch (Exception e) {
            LOGGER.error("[ERROR] : {}", e.getMessage());
        }
    }

    private void processCommand(Command command) {
        if (command.isCreation()) {
            outputView.printVoucherType();

            String input = inputView.input();
            VoucherType voucherType = VoucherType.from(input);
            printVoucherMessage(voucherType);

            String amount = inputView.input();
            amount = checkInputAmount(amount, voucherType);
            VoucherRequest voucherRequest = VoucherRequest.of(Integer.parseInt(amount), voucherType);
            voucherService.createVoucher(voucherRequest);
            outputView.printSuccessMessage();
        }
        else if (command.isInquiry()) {
            VoucherListResponse voucherListResponse = voucherService.getAllVoucher();
            outputView.printVoucherList(voucherListResponse);
        }
    }

    private boolean isContinue(Command command) {
        if (command.isExit()) {
            return false;
        }

        return true;
    }

    private String checkInputAmount(String amount, VoucherType voucherType) {
        while (!isAmountValid(amount, voucherType)) {
            outputView.printInvalidRangeMessage();
            printVoucherMessage(voucherType);
            amount = inputView.input();
        }

        return amount;
    }

    private void printVoucherMessage(VoucherType voucherType) {
        if (voucherType.isFixedAmount()) {
            outputView.printFixedDiscountVoucherMessage();
            return;
        }

        outputView.printPercentDiscountVoucherMessage();
    }

    private boolean isAmountValid(String amount, VoucherType voucherType) {
        if (voucherType.isPercentAmount()) {
            return PERCENT_INPUT_RANGE_PATTERN.matcher(amount).matches();
        }

        return FIXED_INPUT_PATTERN.matcher(amount).matches();
    }
}