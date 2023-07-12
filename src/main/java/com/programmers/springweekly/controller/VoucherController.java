package com.programmers.springweekly.controller;

import com.programmers.springweekly.dto.voucher.request.VoucherCreateRequest;
import com.programmers.springweekly.dto.voucher.request.VoucherUpdateRequest;
import com.programmers.springweekly.dto.voucher.response.VoucherListResponse;
import com.programmers.springweekly.dto.voucher.response.VoucherResponse;
import com.programmers.springweekly.service.VoucherService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class VoucherController {

    private final VoucherService voucherService;

    public void save(VoucherCreateRequest voucherCreateRequest) {
        voucherService.save(voucherCreateRequest);
    }

    public void update(VoucherUpdateRequest voucherUpdateRequest) {
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

    public void existById(UUID voucherId) {
        voucherService.existById(voucherId);
    }
    
}
