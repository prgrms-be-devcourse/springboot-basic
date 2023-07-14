package com.programmers.voucher.domain.voucher.controller;

import com.programmers.voucher.domain.voucher.dto.VoucherCreateRequest;
import com.programmers.voucher.domain.voucher.dto.VoucherResponse;
import com.programmers.voucher.domain.voucher.dto.VoucherUpdateRequest;
import com.programmers.voucher.domain.voucher.service.VoucherService;
import com.programmers.voucher.view.Input;
import com.programmers.voucher.view.Output;
import com.programmers.voucher.view.command.VoucherCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class VoucherConsoleController {
    private final Input input;
    private final Output output;
    private final VoucherService voucherService;

    public void run() {
        output.displayVoucherCommands();
        VoucherCommand command = input.readVoucherCommand();

        switch (command) {
            case CREATE -> {
                VoucherResponse voucher = createVoucher();
                output.displayVoucher(voucher);
            }
            case READ_ALL -> {
                List<VoucherResponse> vouchers = voucherService.getAllVouchers();
                vouchers.forEach(output::displayVoucher);
            }
            case READ -> {
                VoucherResponse voucher = getVoucher();
                output.displayVoucher(voucher);
            }
            case UPDATE -> {
                VoucherResponse voucher = updateVoucher();
                output.displayVoucher(voucher);
            }
            case DELETE -> deleteVoucher();
        }
    }

    private VoucherResponse createVoucher() {
        String voucherType = input.readVoucherType();
        int discountAmount = input.readDiscountAmount();
        VoucherCreateRequest voucherCreateRequest = VoucherCreateRequest.of(voucherType, discountAmount);

        return voucherService.createVoucher(voucherCreateRequest);
    }

    private VoucherResponse getVoucher() {
        UUID voucherId = input.readUUID();
        return voucherService.getVoucher(voucherId);
    }

    private VoucherResponse updateVoucher() {
        UUID voucherId = input.readUUID();
        String voucherType = input.readVoucherType();
        int discountAmount = input.readDiscountAmount();
        VoucherUpdateRequest voucherUpdateRequest = VoucherUpdateRequest.of(voucherType, discountAmount);

        return voucherService.updateVoucher(voucherId, voucherUpdateRequest);
    }

    private void deleteVoucher() {
        UUID voucherId = input.readUUID();
        voucherService.deleteVoucher(voucherId);
    }
}
