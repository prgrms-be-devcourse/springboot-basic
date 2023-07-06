package com.programmers.voucher.controller.voucher;

import com.programmers.voucher.controller.voucher.dto.VoucherCreateRequest;
import com.programmers.voucher.controller.voucher.dto.VoucherResponse;
import com.programmers.voucher.entity.voucher.Voucher;
import com.programmers.voucher.service.VoucherService;
import com.programmers.voucher.view.Input;
import com.programmers.voucher.view.Output;
import com.programmers.voucher.view.dto.DiscountAmount;
import com.programmers.voucher.view.dto.VoucherCommand;
import com.programmers.voucher.view.dto.VoucherType;
import org.springframework.stereotype.Controller;

import java.util.List;

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
        output.displayVoucherCommands();
        VoucherCommand command = input.readVoucherCommand();

        switch (command) {
            case CREATE -> {
                output.displayVoucherType();
                VoucherResponse voucher = createVoucher();
                output.displayCreatedVoucher(voucher);
            }
            case READ -> getVoucherList();
            case READ_ALL -> getVoucherList();
            case UPDATE -> getVoucherList();
            case DELETE -> getVoucherList();
        }
    }

    private VoucherResponse createVoucher() {
        VoucherType voucherType = input.readVoucherType();
        DiscountAmount discountAmount = input.readDiscountAmount(voucherType);
        VoucherCreateRequest voucherCreateRequest = VoucherCreateRequest.of(voucherType, discountAmount);

        return voucherService.createVoucher(voucherCreateRequest);
    }

    private List<Voucher> getVoucherList() {
        return voucherService.getVoucherList();
    }
}
