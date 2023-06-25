package com.programmers.voucher.controller;

import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.view.*;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Controller
public class VoucherController implements Runnable {
    private final Input input;
    private final Output output;

    public VoucherController(Input input, Output output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public void run() {
        AtomicBoolean running = new AtomicBoolean(true);

        while (running.get()) {
            output.displayCommands();
            Command command = input.readCommand();

            switch (command) {
                case EXIT -> running.set(false);
                case CREATE -> {
                    output.displayVoucherCommands();
                    VoucherCommand voucherCommand = input.readVoucherCommand();
                    DiscountAmount discountAmount = input.readDiscountAmount(voucherCommand);
                }
                case LIST -> {
                    getVoucherList();
                }
            }
        }
    }

    private Voucher createVoucher() {
        System.out.println("create");
        return null;
    }

    private List<Voucher> getVoucherList() {
        System.out.println("list");
        return null;
    }
}
