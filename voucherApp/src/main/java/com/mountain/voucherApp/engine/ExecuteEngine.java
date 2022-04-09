package com.mountain.voucherApp.engine;

import com.mountain.voucherApp.enums.DiscountPolicy;
import com.mountain.voucherApp.io.Console;
import com.mountain.voucherApp.voucher.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.mountain.voucherApp.utils.MenuUtil.*;

@Component
public class ExecuteEngine {

    private final VoucherService voucherService;
    private final Console console;

    @Autowired
    public ExecuteEngine(VoucherService voucherService, Console console) {
        this.voucherService = voucherService;
        this.console = console;
    }

    private void createVoucher(String command) {
        console.choiceDiscountPolicy();
        try {
            int seq = Integer.valueOf(console.input());
            if (seq > DiscountPolicy.values().length) {
                console.printWrongInput(command);
                return ;
            }
            console.printAmount();
            long amount = Long.valueOf(console.input());
            voucherService.createVoucher(seq, amount);
        } catch (NumberFormatException e) {
            console.printWrongInput(command);
        }
    }

    public void run() {
        while (true) {
            console.printManual();
            String command = console.input().toLowerCase().trim();
            if (isExit(command)) {
                console.close();
                break;
            } else if (isCreate(command)) {
                createVoucher(command);
            } else if (isList(command)) {
                console.printAllList(voucherService.findAll());
            } else {
                console.printWrongInput(command);
            }
        }
    }
}
