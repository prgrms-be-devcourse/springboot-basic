package kdt.vouchermanagement.domain.voucher.controller;

import kdt.vouchermanagement.domain.voucher.dto.VoucherRequest;
import kdt.vouchermanagement.global.response.Response;

public class VoucherConsoleController {

    private final VoucherService voucherService;

    public VoucherConsoleController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public Response create(VoucherRequest request) {
    }
}
