package org.prgrms.voucher.controller;


import org.prgrms.voucher.dto.VoucherDto;
import org.prgrms.voucher.response.ResponseState;
import org.prgrms.voucher.service.VoucherService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class VoucherWebController {

    private final VoucherService voucherService;

    public VoucherWebController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @PostMapping("/api/v1/voucher/new")
    public ResponseState createVoucher(@RequestBody @Valid VoucherDto.VoucherRequest request) {

        voucherService.create(request);

        return ResponseState.SUCCESS;
    }

    @GetMapping("/api/v1/vouchers")
    public List<VoucherDto.VoucherResponse> findVouchers() {

        return voucherService.list().stream()
                .map(VoucherDto.VoucherResponse::from)
                .toList();
    }
}