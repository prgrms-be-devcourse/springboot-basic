package com.example.voucher.voucher.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import com.example.voucher.constant.ResponseStatus;
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
        try {
            VoucherDTO voucher = voucherService.createVoucher(request.getVoucherType(), request.getDiscountValue());

            return new VoucherResponse(ResponseStatus.SC, voucher);
        } catch (Exception e) {
            return new VoucherResponse(ResponseStatus.ER, e.getMessage());
        }
    }

    public VoucherResponse getVouchers() {
        try {
            List<VoucherDTO> vouchers = voucherService.getVouchers();

            return new VoucherResponse(ResponseStatus.SC, vouchers);
        } catch (Exception e) {
            return new VoucherResponse(ResponseStatus.ER, e.getMessage());
        }
    }

    public VoucherResponse getVoucher(VoucherRequest request) {
        try {
            VoucherDTO voucher = voucherService.getVoucher(request.getVoucherId());

            return new VoucherResponse(ResponseStatus.SC, voucher);
        } catch (Exception e) {
            return new VoucherResponse(ResponseStatus.ER, e.getMessage());
        }
    }

    public VoucherResponse update(VoucherRequest request) {
        try {
            VoucherDTO voucher = voucherService.update(request.getVoucherId(), request.getVoucherType(),
                request.getDiscountValue());

            return new VoucherResponse(ResponseStatus.SC, voucher);
        } catch (Exception e) {
            return new VoucherResponse(ResponseStatus.ER, e.getMessage());
        }

    }

    public VoucherResponse deleteVouchers() {
        try {
            voucherService.deleteVouchers();

            return new VoucherResponse(ResponseStatus.SC);
        } catch (Exception e) {
            return new VoucherResponse(ResponseStatus.ER, e.getMessage());
        }
    }

    public VoucherResponse deleteVoucher(VoucherRequest request) {
        try {
            voucherService.deleteVoucher(request.getVoucherId());

            return new VoucherResponse(ResponseStatus.SC);
        } catch (Exception e) {
            return new VoucherResponse(ResponseStatus.ER, e.getMessage());
        }
    }

}
