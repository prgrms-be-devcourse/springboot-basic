package kdt.vouchermanagement.domain.voucher.controller;

import kdt.vouchermanagement.domain.voucher.domain.Voucher;
import kdt.vouchermanagement.domain.voucher.domain.VoucherType;
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
            validateVoucherType(request);
            Voucher savedVoucher = voucherService.createVoucher(request.toDomain());
            return Response.of(200, savedVoucher);
        } catch (IllegalArgumentException exception) {
            return Response.of(400, exception.getMessage());
        }
    }

    private void validateVoucherType(VoucherRequest request) {
        if (request.getVoucherType().equals(VoucherType.NONE)) {
            throw new IllegalArgumentException("입력한 VoucherType 값이 유효하지 않습니다.");
        }
    }
}
