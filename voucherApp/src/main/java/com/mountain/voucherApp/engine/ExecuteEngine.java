package com.mountain.voucherApp.engine;

import com.mountain.voucherApp.enums.DiscountPolicy;
import com.mountain.voucherApp.io.Console;
import com.mountain.voucherApp.voucher.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.mountain.voucherApp.utils.MenuUtil.*;

@Component
public class ExecuteEngine {

    private static final Logger log = LoggerFactory.getLogger(ExecuteEngine.class);

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
            log.error("number format error");
            console.printWrongInput(command);
        }
    }

    public void run() {
        while (true) {
            console.printManual();
            String command = console.input().toLowerCase().trim();
            if (isExit(command)) {
                log.info("program exit");
                console.close();
                break;
            } else if (isCreate(command)) {
                log.info("create new voucher");
                createVoucher(command);
            } else if (isList(command)) {
                log.info("select voucher list");
                console.printAllList(voucherService.findAll());
            } else {
                log.error("wrong input error");
                console.printWrongInput(command);
            }
        }
    }
}
