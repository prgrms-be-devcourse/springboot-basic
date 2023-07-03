package com.programmers.springbasic.domain.voucher.controller;

import com.programmers.springbasic.domain.io.IOConsole;
import com.programmers.springbasic.domain.voucher.validator.*;
import com.programmers.springbasic.domain.voucher.view.VoucherCommandOption;
import com.programmers.springbasic.domain.voucher.view.VoucherOption;
import com.programmers.springbasic.domain.voucher.service.VoucherService;
import com.programmers.springbasic.domain.voucher.view.VoucherCommandMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class VoucherController {
    private static final Logger logger = LoggerFactory.getLogger(VoucherController.class);

    private final VoucherService voucherService;
    private final IOConsole ioConsole;


    public void run() {
        ioConsole.showVoucherMenu();

        String inputCommand = ioConsole.getInput();
        VoucherCommandValidator voucherCommandValidator = new VoucherCommandValidator(inputCommand);

        handleCommand(voucherCommandValidator);
    }

    private void handleCommand(VoucherCommandValidator voucherCommandValidator) {
        String command = voucherCommandValidator.getInputCommand();

        if (command.equals(VoucherCommandOption.CREATE.getCommand())) {
            executeCreateVoucher();
            return;
        }

        if (command.equals(VoucherCommandOption.LIST.getCommand())) {
            executeListVoucher();
            return;
        }

        if (command.equals(VoucherCommandOption.READ.getCommand())) {
            executeRaedVoucher();
            return;
        }

        if (command.equals(VoucherCommandOption.UPDATE.getCommand())) { // TODO: update voucher
            executeUpdateVoucher();
            return;
        }

        if (command.equals(VoucherCommandOption.DELETE.getCommand())) {
            executeDeleteVoucher();
            return;
        }

        if (command.equals(VoucherCommandOption.SHOW.getCommand())) {
            executeShowVoucher();
            return;
        }

        if (command.equals(VoucherCommandOption.EXIT.getCommand())) {
            ioConsole.printSingleOutput("Go back to Main Menu!");
        }
    }

    private void executeCreateVoucher() {
        ioConsole.printSingleOutput(VoucherCommandMessage.VOUCHER_OPTION_MESSAGE.getMessage());

        String inputVoucherOption = ioConsole.getInput();
        VoucherOptionValidator voucherOptionValidator = new VoucherOptionValidator(inputVoucherOption);

        String voucherOption = voucherOptionValidator.getVoucherOption();

        ioConsole.printSingleOutput("Voucher를 할당할 Customer Id를 입력해주세요");
        String inputCustomerId = ioConsole.getInput();
        VoucherCodeValidator voucherCodeValidator = new VoucherCodeValidator(inputCustomerId);

        if (voucherOption.equals(VoucherOption.FIXED_AMOUNT_VOUCHER.getVoucherOption())) {
            ioConsole.printSingleOutput(VoucherCommandMessage.FIXED_AMOUNT_INPUT_MESSAGE.getMessage());
            String fixedAmountInput = ioConsole.getInput();
            FixedAmountVoucherCreateRequestValidator fixedAmountVoucherCreateRequestValidator = new FixedAmountVoucherCreateRequestValidator(fixedAmountInput);

            voucherService.createFixedAmountVoucher(fixedAmountVoucherCreateRequestValidator, voucherCodeValidator);
            ioConsole.printSingleOutput(VoucherCommandMessage.FIXED_AMOUNT_VOUCHER_CREATE_MESSAGE.getMessage());
        }

        if (voucherOption.equals(VoucherOption.PERCENT_DISCOUNT_VOUCHER.getVoucherOption())) {
            ioConsole.printSingleOutput(VoucherCommandMessage.PERCENT_DISCOUNT_INPUT_MESSAGE.getMessage());
            String percentDiscountInput = ioConsole.getInput();
            PercentDiscountVoucherCreateRequestValidator percentDiscountVoucherCreateRequestValidator = new PercentDiscountVoucherCreateRequestValidator(percentDiscountInput);

            voucherService.createPercentDiscountVoucher(percentDiscountVoucherCreateRequestValidator, voucherCodeValidator);
            ioConsole.printSingleOutput(VoucherCommandMessage.PERCENT_DISCOUNT_VOUCHER_CREATE_MESSAGE.getMessage());
        }
    }

    private void executeListVoucher() {
        ioConsole.printSingleOutput(VoucherCommandMessage.VOUCHER_OPTION_MESSAGE.getMessage());

        String inputVoucherOption = ioConsole.getInput();
        VoucherOptionValidator voucherOptionValidator = new VoucherOptionValidator(inputVoucherOption);

        String voucherOption = voucherOptionValidator.getVoucherOption();

        if (voucherOption.equals(VoucherOption.FIXED_AMOUNT_VOUCHER.getVoucherOption())) {
            ioConsole.printListOutput(voucherService.getAllFixedAmountVoucherInfo());
        }

        if (voucherOption.equals(VoucherOption.PERCENT_DISCOUNT_VOUCHER.getVoucherOption())) {
            ioConsole.printListOutput(voucherService.getAllPercentDiscountVoucherInfo());
        }
    }

    private void executeRaedVoucher() {
        ioConsole.printSingleOutput("조회하고자 하는 voucher의 code를 입력해주세요.");

        String voucherCodeInput = ioConsole.getInput();
        VoucherCodeValidator voucherCodeValidator = new VoucherCodeValidator(voucherCodeInput);

        ioConsole.printSingleOutput(voucherService.findVoucher(voucherCodeValidator));
    }

    private void executeUpdateVoucher() {   // TODO: update voucher
    }

    private void executeDeleteVoucher() {
        ioConsole.printSingleOutput("삭제하고자 하는 voucher의 code를 입력해주세요.");

        String voucherCodeInput = ioConsole.getInput();
        VoucherCodeValidator voucherCodeValidator = new VoucherCodeValidator(voucherCodeInput);

        voucherService.removeVoucher(voucherCodeValidator);
    }

    private void executeShowVoucher() {
        ioConsole.printSingleOutput("특정 voucher를 소지한 고객의 명단을 보여줍니다. voucher type을 입력해주세요. (FIXED or PERCENT)");

        String inputVoucherOption = ioConsole.getInput();
        VoucherOptionValidator voucherOptionValidator = new VoucherOptionValidator(inputVoucherOption);

        ioConsole.printListOutput(voucherService.getAllCustomerIdByVoucherType(voucherOptionValidator));
    }
}
