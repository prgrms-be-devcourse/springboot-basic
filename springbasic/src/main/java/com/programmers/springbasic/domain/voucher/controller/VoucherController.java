package com.programmers.springbasic.domain.voucher.controller;

import com.programmers.springbasic.domain.io.IOConsole;
import com.programmers.springbasic.domain.voucher.dto.request.VoucherCreateRequestDTO;
import com.programmers.springbasic.domain.voucher.validator.*;
import com.programmers.springbasic.domain.voucher.view.VoucherCommandOption;
import com.programmers.springbasic.domain.voucher.model.VoucherType;
import com.programmers.springbasic.domain.voucher.service.VoucherService;
import com.programmers.springbasic.domain.voucher.view.VoucherCommandMessage;
import com.programmers.springbasic.domain.voucher.view.VoucherConsoleResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequiredArgsConstructor
public class VoucherController {
    private static final Logger logger = LoggerFactory.getLogger(VoucherController.class);

    private final VoucherService voucherService;
    private final IOConsole ioConsole;


    public void run() {
        ioConsole.showVoucherMenu();

        String inputCommand = ioConsole.getInput();
        VoucherCommandValidator.validateCommand(inputCommand);

        handleCommand(inputCommand);
    }

    private void handleCommand(String command) {
        switch (VoucherCommandOption.of(command)) {
            case CREATE: {
                executeCreateVoucher();
                break;
            }
            case LIST: {
                executeListVoucher();
                break;
            }
            case READ: {
                executeRaedVoucher();
                break;
            }
            case UPDATE: {
                executeUpdateVoucher();
                break;
            }
            case DELETE: {
                executeDeleteVoucher();
                break;
            }
            case SHOW: {
                executeShowVoucher();
                break;
            }
            case EXIT: {
                ioConsole.printSingleOutput("Go back to Main Menu!");
            }
        }
    }

    private void executeCreateVoucher() {
        ioConsole.printSingleOutput(VoucherCommandMessage.VOUCHER_OPTION_MESSAGE.getMessage());

        String voucherType = ioConsole.getInput();
        VoucherOptionValidator.validateOption(voucherType);

        ioConsole.printSingleOutput("Voucher를 할당할 Customer Id를 입력해주세요");
        String inputCustomerId = ioConsole.getInput();
        VoucherCodeValidator.validateVoucherCode(inputCustomerId);

        switch (VoucherType.of(voucherType)) {
            case FIXED_AMOUNT_VOUCHER: {
                ioConsole.printSingleOutput(VoucherCommandMessage.FIXED_AMOUNT_INPUT_MESSAGE.getMessage());
                String fixedAmountInput = ioConsole.getInput();
                FixedAmountVoucherCreateRequestValidator.validateFixedAmount(fixedAmountInput);

                VoucherCreateRequestDTO createRequestDTO = new VoucherCreateRequestDTO(inputCustomerId, voucherType, Double.parseDouble(fixedAmountInput));
                voucherService.createVoucher(createRequestDTO);
                ioConsole.printSingleOutput(VoucherCommandMessage.FIXED_AMOUNT_VOUCHER_CREATE_MESSAGE.getMessage());
                break;
            }
            case PERCENT_DISCOUNT_VOUCHER: {
                ioConsole.printSingleOutput(VoucherCommandMessage.PERCENT_DISCOUNT_INPUT_MESSAGE.getMessage());
                String percentDiscountInput = ioConsole.getInput();
                PercentDiscountVoucherCreateRequestValidator.validatePercentDiscount(percentDiscountInput);

                VoucherCreateRequestDTO createRequestDTO = new VoucherCreateRequestDTO(inputCustomerId, voucherType, Double.parseDouble(percentDiscountInput));
                voucherService.createVoucher(createRequestDTO);
                ioConsole.printSingleOutput(VoucherCommandMessage.PERCENT_DISCOUNT_VOUCHER_CREATE_MESSAGE.getMessage());
            }
        }
    }

    private void executeListVoucher() {
        ioConsole.printSingleOutput(VoucherCommandMessage.VOUCHER_OPTION_MESSAGE.getMessage());

        String inputVoucherType = ioConsole.getInput();
        VoucherOptionValidator.validateOption(inputVoucherType);

        List<String> voucherInfos = voucherService.getAllVoucherInfo(inputVoucherType).stream()
                .map(VoucherConsoleResponse::getVoucherInfo)
                .collect(Collectors.toList());

        ioConsole.printListOutput(voucherInfos);
    }

    private void executeRaedVoucher() {
        ioConsole.printSingleOutput("조회하고자 하는 voucher의 code를 입력해주세요.");

        String voucherCodeInput = ioConsole.getInput();
        VoucherCodeValidator.validateVoucherCode(voucherCodeInput);

        ioConsole.printSingleOutput(VoucherConsoleResponse.getVoucherInfo(voucherService.findVoucher(voucherCodeInput)));
    }

    private void executeUpdateVoucher() {   // TODO: update voucher
    }

    private void executeDeleteVoucher() {
        ioConsole.printSingleOutput("삭제하고자 하는 voucher의 code를 입력해주세요.");

        String voucherCodeInput = ioConsole.getInput();
        VoucherCodeValidator.validateVoucherCode(voucherCodeInput);

        voucherService.removeVoucher(voucherCodeInput);
    }

    private void executeShowVoucher() {
        ioConsole.printSingleOutput("특정 voucher를 소지한 고객의 명단을 보여줍니다. voucher type을 입력해주세요. (FIXED or PERCENT)");

        String inputVoucherOption = ioConsole.getInput();
        VoucherOptionValidator.validateOption(inputVoucherOption);

        // ioConsole.printListOutput(voucherService.getAllCustomerIdByVoucherType(inputVoucherOption));
    }
}
