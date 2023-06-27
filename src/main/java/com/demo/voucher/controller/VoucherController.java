package com.demo.voucher.controller;

import com.demo.voucher.domain.VoucherType;
import com.demo.voucher.io.CommandType;
import com.demo.voucher.io.Input;
import com.demo.voucher.io.Output;
import com.demo.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class VoucherController implements Runnable {
    private static final String REQUEST_MENU_TYPE_PROMPT = "\n명령 메뉴를 입력해주세요 : ";
    private static final String INPUT_COMMAND_ERROR = "올바른 명령어를 입력하지 않았습니다.";
    private static final String INPUT_VOUCHER_TYPE_ERROR = "올바른 바우처 타입을 입력하지 않았습니다.";
    private static final String INPUT_AMOUNT_ERROR = "올바른 할인 금액을 입력하지 않았습니다.";
    private static final String EXIT = "exit";
    private static final String CREATE_VOUCHER = "create";
    private static final String LIST_ALL_VOUCHERS = "list";
    private static final String REQUEST_VOUCHER_TYPE_PROMPT = "생성할 바우처 타입을 입력해주세요 : ";

    private final Input input;
    private final Output output;
    private final VoucherService voucherService;

    public VoucherController(Input input, Output output, VoucherService voucherService) {
        this.input = input;
        this.output = output;
        this.voucherService = voucherService;
    }

    @Override
    public void run() {
        output.showMenu();

        boolean isProgramRunnable = true;
        while (isProgramRunnable) {
            String inputCommand = input.getMenu(REQUEST_MENU_TYPE_PROMPT);
            if (!CommandType.isValidCommandInput(inputCommand)) {
                output.inputError(INPUT_COMMAND_ERROR);
                continue;
            }

            switch (inputCommand) {
                case EXIT -> isProgramRunnable = false;
                case CREATE_VOUCHER -> {
                    output.showVoucherType();

                    String inputVoucherType = input.getVoucherType(REQUEST_VOUCHER_TYPE_PROMPT);
                    Optional<VoucherType> voucherType = VoucherType.getVoucherTypeByCommand(inputVoucherType);
                    if (voucherType.isEmpty()) {
                        output.inputError(INPUT_VOUCHER_TYPE_ERROR);
                        continue;
                    }

                    String inputAmount = input.getAmount(voucherType.get());
                    if (!voucherType.get().validateAmount(inputAmount)) {
                        output.inputError(INPUT_AMOUNT_ERROR);
                        continue;
                    }
                    voucherService.createVoucher(voucherType.get(), inputAmount);
                }
                case LIST_ALL_VOUCHERS -> output.showAllVouchers(voucherService.findAllVouchers());
            }
        }
    }
}
