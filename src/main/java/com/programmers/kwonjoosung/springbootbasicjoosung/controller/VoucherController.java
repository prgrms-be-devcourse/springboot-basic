package com.programmers.kwonjoosung.springbootbasicjoosung.controller;


import com.programmers.kwonjoosung.springbootbasicjoosung.console.Console;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.VoucherType;
import com.programmers.kwonjoosung.springbootbasicjoosung.service.VoucherService;
import org.springframework.stereotype.Component;

@Component
public class VoucherController {
    private final VoucherService voucherService;
    private final Console console;

    VoucherController(VoucherService voucherService, Console console) {
        this.voucherService = voucherService;
        this.console = console;
    }

    public void selectFunction(CommandType commandType) {
        switch (commandType) {
            case CREATE -> createVoucher();
            case LIST -> showVoucherList();
        }
    }

    private void createVoucher() {  // controller에서 담당해도 되는 로직 인지 궁긍합니다.?
        VoucherType voucherType = VoucherType.getVoucherType(console.inputVoucherType());
        long discount = console.inputDiscount();
        var voucher = voucherService.createAndSave(voucherType, discount);
        console.showVoucher(voucher);
    }

    private void showVoucherList() {
        voucherService.getVoucherList().forEach(console::showVoucher);
    }

}
