package com.pgms.part1.domain.voucher.controller;

import com.pgms.part1.domain.view.ConsoleView;
import com.pgms.part1.domain.voucher.dto.VoucherCreateRequestDto;
import com.pgms.part1.domain.voucher.dto.VoucherMenuRequestDto;
import com.pgms.part1.domain.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;

@Controller
public class VoucherController {
    private final ConsoleView consoleView;
    private final VoucherService voucherService;

    public VoucherController(ConsoleView consoleView, VoucherService voucherService) {
        this.consoleView = consoleView;
        this.voucherService = voucherService;
    }

    public void init() {
        VoucherMenuRequestDto voucherMenuRequestDto = consoleView.init();

        switch (voucherMenuRequestDto.command()){
            case "create" : createVoucher();
            case "list" : listVoucher();
            case "exit" : exitVoucher();
        }
    }

    public void createVoucher(){
        VoucherCreateRequestDto voucherCreateRequestDto = consoleView.createVoucher();

        switch (voucherCreateRequestDto.command()){
            case "1" : voucherService.createFixedAmountVoucher(voucherCreateRequestDto);
            case "2" : voucherService.createPercentDiscountVoucher(voucherCreateRequestDto);
        }
        init();
    }

    public void listVoucher(){

    }

    public void exitVoucher(){

    }
}
