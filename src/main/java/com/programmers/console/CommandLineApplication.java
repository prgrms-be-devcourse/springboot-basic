package com.programmers.console;

import com.programmers.global.exception.DiscountValueException;
import com.programmers.global.exception.VoucherCommandException;
import com.programmers.console.util.Command;
import com.programmers.console.view.Console;
import com.programmers.voucher.controller.VoucherController;
import com.programmers.voucher.domain.Discount;
import com.programmers.voucher.domain.VoucherType;
import com.programmers.voucher.dto.VoucherRequestDto;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CommandLineApplication {
    private boolean isRunning = true;

    private final VoucherController voucherController;
    private final Console console;

    public CommandLineApplication(Console console, VoucherController voucherController) {
        this.console = console;
        this.voucherController = voucherController;
    }

    public void run() {
        while (isRunning) {
            try {
                Command command = Command.of(console.inputMenu());
                execute(command);
            } catch (RuntimeException e) {
                console.println(e.getMessage());
            }
        }
    }

    private void execute(Command command) {
        switch (command) {
            case EXIT -> isRunning = false;
            case CREATE -> create();
            case LIST -> findAll();
        }
    }

    private void create() {
        VoucherType voucherType = inputVoucherInfo();
        Discount discount = Discount.of(voucherType, inputDiscountInfo());
        while (true) {
            try {
                createVoucher(discount);
                return;
            } catch (Exception e) {
                console.print(e.getMessage());
            }
        }
    }

    private VoucherType inputVoucherInfo() {
        while (true) {
            try {
                return VoucherType.of(console.inputVoucherType());
            } catch (VoucherCommandException e) {
                console.println(e.getMessage());
            }
        }
    }

    private long inputDiscountInfo() {
        while (true) {
            try {
                return Long.parseLong(console.inputDiscountValue());
            } catch (DiscountValueException e) {
                console.print(e.getMessage());
            }
        }
    }

    private void createVoucher(Discount discount) {
        VoucherRequestDto requestDto = new VoucherRequestDto(UUID.randomUUID(), discount);
        console.printVoucher(voucherController.create(requestDto));
    }

    private void findAll() {
        console.printVouchers(voucherController.findAll());
    }
}
