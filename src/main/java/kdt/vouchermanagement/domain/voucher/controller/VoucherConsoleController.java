package kdt.vouchermanagement.domain.voucher.controller;

import kdt.vouchermanagement.domain.voucher.domain.Voucher;
import kdt.vouchermanagement.domain.voucher.dto.VoucherRequest;
import kdt.vouchermanagement.domain.voucher.service.VoucherService;
import kdt.vouchermanagement.global.response.Response;

public class VoucherConsoleController {

    private final VoucherService voucherService;

    public VoucherConsoleController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public Response create(VoucherRequest request) {
        try {
            Voucher savedVoucher = voucherService.createVoucher(request.toDomain());
            return Response.of(200, savedVoucher);
        } catch (IllegalArgumentException exception) {
            return Response.of(400, exception.getMessage());
        }
    }
}
