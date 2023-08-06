package com.programmers.springbootbasic.presentation.controller;

import com.programmers.springbootbasic.common.domain.Page;
import com.programmers.springbootbasic.common.domain.Pageable;
import com.programmers.springbootbasic.presentation.controller.dto.Voucher.VoucherCreationRequest;
import com.programmers.springbootbasic.service.VoucherService;
import com.programmers.springbootbasic.service.dto.Voucher.VoucherResponse;
import com.programmers.springbootbasic.service.dto.Voucher.VoucherResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/voucher")
public class VoucherRestController {
    private final VoucherService voucherService;

    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UUID createVoucher(@RequestBody @Valid VoucherCreationRequest request) {
        return voucherService.createVoucher(request);
    }

    @GetMapping("/vouchers")
    @ResponseStatus(HttpStatus.OK)
    public Page<VoucherResponse> findVouchers(@ModelAttribute Pageable pageable) {
        return voucherService.findVoucherByPage(pageable);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public VoucherResponses listVouchers() {
        return voucherService.list();
    }
}
