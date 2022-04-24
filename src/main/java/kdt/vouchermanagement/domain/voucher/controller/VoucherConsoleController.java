package kdt.vouchermanagement.domain.voucher.controller;

import kdt.vouchermanagement.domain.voucher.domain.Voucher;
import kdt.vouchermanagement.domain.voucher.dto.VoucherRequest;
import kdt.vouchermanagement.domain.voucher.exception.DuplicateVoucherException;
import kdt.vouchermanagement.domain.voucher.service.VoucherService;
import kdt.vouchermanagement.global.response.Response;
import org.springframework.stereotype.Controller;

@Controller
public class VoucherConsoleController {

    private final VoucherService voucherService;

    public VoucherConsoleController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public Response create(VoucherRequest request) {
        if (request == null) {
            return Response.of(400, "애플리케이션이 null을 사용하려고 합니다.");
        }

        try {
            Voucher savedVoucher = voucherService.createVoucher(request.toDomain());
            return Response.of(200, savedVoucher);
        } catch (IllegalArgumentException exception) {
            return Response.of(400, exception.getMessage());
        } catch (DuplicateVoucherException exception) {
            return Response.of(400, exception.getMessage());
        }
    }
}
