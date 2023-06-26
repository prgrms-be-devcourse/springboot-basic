package com.programmers.voucher.controller;

import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.service.VoucherService;
import com.programmers.voucher.view.Input;
import com.programmers.voucher.view.Output;
import com.programmers.voucher.view.dto.Command;
import com.programmers.voucher.view.dto.DiscountAmount;
import com.programmers.voucher.view.dto.VoucherCommand;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

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

                    Voucher voucher = createVoucher(voucherCommand, discountAmount);
                    output.displayCreatedVoucher(voucher);
                }
                case LIST -> getVoucherList().forEach(output::displayVoucher);
            }
        }
    }

    private Voucher createVoucher(VoucherCommand voucherCommand, DiscountAmount discountAmount) {
        return voucherService.createVoucher(voucherCommand, discountAmount);
    }

    private List<Voucher> getVoucherList() {
        return voucherService.getVoucherList();
    }
}
