package com.programmers.voucher.controller.voucher;

import com.programmers.voucher.controller.voucher.dto.VoucherCreateRequest;
import com.programmers.voucher.controller.voucher.dto.VoucherResponse;
import com.programmers.voucher.controller.voucher.dto.VoucherUpdateRequest;
import com.programmers.voucher.entity.voucher.VoucherType;
import com.programmers.voucher.service.VoucherService;
import com.programmers.voucher.view.Input;
import com.programmers.voucher.view.Output;
import com.programmers.voucher.view.command.VoucherCommand;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

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
            case READ_ALL -> getAllVouchers().forEach(output::displayVoucher);
            case READ -> output.displayVoucher(getVoucher());
            case UPDATE -> output.displayVoucher(updateVoucher());
            case DELETE -> deleteVoucher();
        }
    }

    private VoucherResponse createVoucher() {
        VoucherType voucherType = input.readVoucherType();
        int discountAmount = input.readDiscountAmount();
        VoucherCreateRequest voucherCreateRequest = VoucherCreateRequest.of(voucherType, discountAmount);

        return voucherService.createVoucher(voucherCreateRequest);
    }

    private List<VoucherResponse> getAllVouchers() {
        return voucherService.getAllVouchers();
    }

    private VoucherResponse getVoucher() {
        UUID voucherId = input.readUUID();
        return voucherService.getVoucher(voucherId);
    }

    private VoucherResponse updateVoucher() {
        UUID voucherId = input.readUUID();
        output.displayVoucherType();
        VoucherType voucherType = input.readVoucherType();
        int discountAmount = input.readDiscountAmount();
        VoucherUpdateRequest voucherUpdateRequest = VoucherUpdateRequest.of(voucherType, discountAmount);

        return voucherService.updateVoucher(voucherId, voucherUpdateRequest);
    }

    private void deleteVoucher() {
        UUID voucherId = input.readUUID();
        voucherService.deleteVoucher(voucherId);
    }
}
