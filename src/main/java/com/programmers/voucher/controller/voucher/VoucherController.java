package com.programmers.voucher.controller.voucher;

import com.programmers.voucher.controller.voucher.dto.VoucherCreateRequest;
import com.programmers.voucher.controller.voucher.dto.VoucherCreateResponse;
import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.service.VoucherService;
import com.programmers.voucher.view.Input;
import com.programmers.voucher.view.Output;
import com.programmers.voucher.view.dto.DiscountAmount;
import com.programmers.voucher.view.dto.VoucherCommand;
import com.programmers.voucher.view.dto.VoucherType;
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
            output.displayVoucherCommands();
            VoucherCommand command = input.readVoucherCommand();

            switch (command) {
                case CREATE -> {
                    output.displayVoucherType();
                    VoucherCreateResponse voucher = createVoucher();
                    output.displayCreatedVoucher(voucher);
                }
                case READ -> running.set(false);
                case READ_ALL -> running.set(false);
                case UPDATE -> running.set(false);
                case DELETE -> running.set(false);
            }
        }
    }

    private VoucherCreateResponse createVoucher() {
        VoucherType voucherType = input.readVoucherType();
        DiscountAmount discountAmount = input.readDiscountAmount(voucherType);

        return voucherService.createVoucher(VoucherCreateRequest.of(voucherType, discountAmount));
    }

    private List<Voucher> getVoucherList() {
        return voucherService.getVoucherList();
    }
}
