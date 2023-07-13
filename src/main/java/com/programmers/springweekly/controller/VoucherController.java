package com.programmers.springweekly.controller;

import com.programmers.springweekly.dto.voucher.request.VoucherCreateRequest;
import com.programmers.springweekly.dto.voucher.request.VoucherUpdateRequest;
import com.programmers.springweekly.dto.voucher.response.VoucherListResponse;
import com.programmers.springweekly.dto.voucher.response.VoucherResponse;
import com.programmers.springweekly.service.VoucherService;
import com.programmers.springweekly.util.Validator.VoucherValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class VoucherController {

    private final VoucherService voucherService;

    public void save(VoucherCreateRequest voucherCreateRequest) {
        VoucherValidator.validateVoucher(
                voucherCreateRequest.getVoucherType(),
                String.valueOf(voucherCreateRequest.getDiscountAmount())
        );

        voucherService.save(voucherCreateRequest);
    }

    public void update(VoucherUpdateRequest voucherUpdateRequest) {
        VoucherValidator.validateVoucher(
                voucherUpdateRequest.getVoucherType(),
                String.valueOf(voucherUpdateRequest.getDiscountAmount())
        );
        
        voucherService.update(voucherUpdateRequest);
    }

    public VoucherResponse findById(UUID voucherId) {
        return voucherService.findById(voucherId);
    }

    public VoucherListResponse findAll() {
        return voucherService.findAll();
    }

    public void deleteById(UUID voucherId) {
        voucherService.deleteById(voucherId);
    }

    public void deleteAll() {
        voucherService.deleteAll();
    }

    public boolean existById(UUID voucherId) {
        return voucherService.existById(voucherId);
    }

}
