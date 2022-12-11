package com.programmers.assignment.voucher.engine.controller;

import com.programmers.assignment.voucher.engine.model.Voucher;
import com.programmers.assignment.voucher.engine.service.CustomerService;
import com.programmers.assignment.voucher.engine.service.VoucherService;
import com.programmers.assignment.voucher.util.response.CommonResponse;
import com.programmers.assignment.voucher.util.response.ResponseCode;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
public class VoucherApiController {

    private final VoucherService voucherService;

    public VoucherApiController(VoucherService voucherService, CustomerService customerService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/api/vouchers")
    public CommonResponse<List<Voucher>> getVoucherList() {
        var vouchers = voucherService.getAllVouchers();
        return new CommonResponse<>(vouchers);
    }

    @PostMapping("/api/vouchers")
    public CommonResponse<?> createVoucher(String customerId, String discountWay, String discountValue) {
        voucherService.makeVoucher(customerId, discountWay, discountValue);
        return new CommonResponse<>(ResponseCode.SUCCESS);
    }

    @GetMapping("/api/vouchers/{voucherId}")
    public CommonResponse<Voucher> voucherDetails(Model model, @PathVariable UUID voucherId) {
        var voucher = voucherService.getVoucherById(voucherId);
        return new CommonResponse<>(voucher);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public CommonResponse<?> handleNoSuchElementException(NoSuchElementException exception) {
        return new CommonResponse<>(ResponseCode.NOT_FOUND_VOUCHER);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public CommonResponse<?> handleIllegalArgumentException(IllegalArgumentException exception) {
        return new CommonResponse<>(ResponseCode.INVALID_DISCOUNT_VALUE);
    }

    @ExceptionHandler(NoSuchFieldException.class)
    public CommonResponse<?> handleNoSuchFieldException(NoSuchFieldException exception) {
        return new CommonResponse<>(ResponseCode.NOT_AVAILABLE_VOUCHER);
    }
}
