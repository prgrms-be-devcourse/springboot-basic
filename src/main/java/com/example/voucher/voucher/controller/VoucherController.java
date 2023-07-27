package com.example.voucher.voucher.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Controller;
import com.example.voucher.request.VoucherRequest;
import com.example.voucher.response.Response;
import com.example.voucher.voucher.service.dto.VoucherDTO;
import com.example.voucher.voucher.service.VoucherService;

@Controller
public class VoucherController {

    private final VoucherService voucherService;

    private VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public Response<VoucherDTO> createVoucher(VoucherRequest.Create request) {
        VoucherDTO voucher = voucherService.createVoucher(request.getVoucherType(), request.getDiscountValue());
        return new Response<>(voucher);
    }

    public Response<VoucherDTO> getVouchers() {
        List<VoucherDTO> vouchers = voucherService.getVouchers();
        return new Response<>(vouchers);
    }

    public void deleteVouchers() {
        voucherService.deleteVouchers();
    }

    public Response<VoucherDTO> search(UUID voucherId) {

        VoucherDTO voucher = voucherService.search(voucherId);
        return new Response<>(voucher);
    }

    public Response<VoucherDTO> update(VoucherRequest.Update request) {

        VoucherDTO voucher = voucherService.update(request.getVoucherId(), request.getVoucherType(),
            request.getDiscountValue());
        return new Response<>(voucher);
    }

    public void deleteVoucher(UUID voucherId) {
        voucherService.deleteVoucher(voucherId);
    }

}
