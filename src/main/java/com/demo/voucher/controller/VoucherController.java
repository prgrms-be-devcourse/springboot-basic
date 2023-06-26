package com.demo.voucher.controller;

import com.demo.voucher.io.Input;
import com.demo.voucher.io.Output;
import com.demo.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;

@Controller
public class VoucherController implements Runnable {
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
        // TODO: Voucher Program 시작 메뉴 설명 출력 (exit, create, list)

        // TODO: 사용자로부터 COMMAND 입력 받기 (exit, create, list)

        // TODO: exit - 프로그램 종료

        // TODO: create
        // TODO: VoucherType 출력
        // TODO: 사용자로부터 VoucherType 입력 받은 후 DiscountAmount 또는 PercentAmount 입력 받기
        // TODO: VoucherService의 create voucher

        // TODO: list - VoucherService로부터 모든 Voucher 가져오기
    }
}
