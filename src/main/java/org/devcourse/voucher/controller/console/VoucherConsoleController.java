package org.devcourse.voucher.controller.console;

import org.devcourse.voucher.app.VoucherService;
import org.devcourse.voucher.controller.console.dto.VoucherInfoResponse;
import org.devcourse.voucher.controller.console.dto.VoucherSaveRequest;

import java.util.ArrayList;
import java.util.List;

public class VoucherConsoleController {

    private final VoucherService voucherService;

    public VoucherConsoleController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public VoucherInfoResponse createVoucher(VoucherSaveRequest request) {
        return new VoucherInfoResponse(0, "", 0);
    }

    public List<VoucherInfoResponse> listVoucher() {
        return new ArrayList<>();
    }
}
