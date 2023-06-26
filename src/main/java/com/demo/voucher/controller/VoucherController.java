package com.demo.voucher.controller;

import com.demo.voucher.io.CommandType;
import com.demo.voucher.io.Input;
import com.demo.voucher.io.Output;
import com.demo.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;

@Controller
public class VoucherController implements Runnable {
    private static final String INPUT_COMMAND_ERROR = "올바른 명령어를 입력하지 않았습니다.";
    private static final String EXIT = "exit";
    private static final String CREATE_VOUCHER = "create";
    private static final String LIST_ALL_VOUCHERS = "list";
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
        boolean isProgramRunnable = true;

        while (isProgramRunnable) {
            // TODO: Voucher Program 시작 메뉴 설명 출력 (exit, create, list)
            output.showMenu();

            // TODO: 사용자로부터 COMMAND 입력 받기 (exit, create, list)
            String inputCommand = input.getMenu();
            if (!CommandType.isValidCommandInput(inputCommand)) {
                output.inputError(INPUT_COMMAND_ERROR);
                continue;
            }

            switch (inputCommand) {
                // TODO: exit - 프로그램 종료
                case EXIT -> isProgramRunnable = false;
                // TODO: create
                case CREATE_VOUCHER -> {
                    // TODO: VoucherType 출력
                    // TODO: 사용자로부터 VoucherType 입력 받은 후 DiscountAmount 또는 PercentAmount 입력 받기
                    // TODO: VoucherService의 create voucher
                }
                // TODO: list - VoucherService로부터 모든 Voucher 가져오기
                case LIST_ALL_VOUCHERS -> output.showAllVoucher(voucherService.findAllVouchers());
            }
        }
    }
}
