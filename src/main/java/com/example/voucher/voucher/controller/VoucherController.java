package com.example.voucher.voucher.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import com.example.voucher.response.Response;
import com.example.voucher.voucher.service.VoucherService;
import com.example.voucher.voucher.service.dto.VoucherDTO;

@Controller
public class VoucherController {

    private final VoucherService voucherService;

    private VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public Response<VoucherDTO> createVoucher(VoucherRequest request) {
        VoucherDTO voucher = voucherService.createVoucher(request.getVoucherType(), request.getDiscountValue());

        return new Response<>(voucher);
    }

    public Response<VoucherDTO> getVouchers() {
        List<VoucherDTO> vouchers = voucherService.getVouchers();

        return new Response<>(vouchers);
    }

    public Response<VoucherDTO> getVoucher(VoucherRequest request) {
        VoucherDTO voucher = voucherService.search(request.getVoucherId());

        return new Response<>(voucher);
    }

    public Response<VoucherDTO> update(VoucherRequest request) {
        VoucherDTO voucher = voucherService.update(request.getVoucherId(), request.getVoucherType(),
            request.getDiscountValue());

        return new Response<>(voucher);
    }

    public void deleteVouchers() {
        voucherService.deleteVouchers();
    }

    public void deleteVoucher(VoucherRequest request) {
        voucherService.deleteVoucher(request.getVoucherId());
    }

}
