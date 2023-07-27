package com.example.voucher.voucher.controller;

import java.util.List;
import org.springframework.stereotype.Controller;

import com.example.voucher.voucher.controller.model.VoucherRequest;
import com.example.voucher.voucher.controller.model.VoucherResponse;
import com.example.voucher.voucher.service.VoucherService;
import com.example.voucher.voucher.service.dto.VoucherDTO;

@Controller
public class VoucherController {

    private final VoucherService voucherService;

    private VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public VoucherResponse createVoucher(VoucherRequest request) {
        VoucherDTO voucher = voucherService.createVoucher(request.getVoucherType(), request.getDiscountValue());

        return new VoucherResponse(voucher);
    }

    public VoucherResponse getVouchers() {
        List<VoucherDTO> vouchers = voucherService.getVouchers();

        return new VoucherResponse(vouchers);
    }

    public VoucherResponse getVoucher(VoucherRequest request) {
        VoucherDTO voucher = voucherService.getVoucher(request.getVoucherId());

        return new VoucherResponse(voucher);
    }

    public VoucherResponse update(VoucherRequest request) {
        VoucherDTO voucher = voucherService.update(request.getVoucherId(), request.getVoucherType(),
            request.getDiscountValue());

        return new VoucherResponse(voucher);
    }

    public void deleteVouchers() {
        voucherService.deleteVouchers();
    }

    public void deleteVoucher(VoucherRequest request) {
        voucherService.deleteVoucher(request.getVoucherId());
    }

}
