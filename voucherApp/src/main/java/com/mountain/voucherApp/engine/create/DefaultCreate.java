package com.mountain.voucherApp.engine.create;

import com.mountain.voucherApp.enums.DiscountPolicy;
import com.mountain.voucherApp.io.Console;
import com.mountain.voucherApp.voucher.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultCreate implements CreateStrategy {

    private final Console console;
    private final VoucherService voucherService;

    @Autowired
    public DefaultCreate(Console console, VoucherService voucherService) {
        this.console = console;
        this.voucherService = voucherService;
    }

    @Override
    public void create() {
        console.choiceDiscountPolicy();
        try {
            int seq = Integer.valueOf(console.input());
            if (seq > DiscountPolicy.values().length) {
                console.printWrongInput();
                return ;
            }
            console.printAmount();
            long amount = Long.valueOf(console.input());
            voucherService.createVoucher(seq, amount);
        } catch (Exception e) {
            console.printException(e);
        }
    }
}
