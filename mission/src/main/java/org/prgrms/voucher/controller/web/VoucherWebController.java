package org.prgrms.voucher.controller.web;


import org.prgrms.voucher.dto.VoucherDto;
import org.prgrms.voucher.response.ResponseState;
import org.prgrms.voucher.service.VoucherService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/vouchers")
public class VoucherWebController {

    private final VoucherService voucherService;

    public VoucherWebController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @PostMapping
    public void createVoucher(@RequestBody @Valid VoucherDto.VoucherRequest request) {

        voucherService.create(request);
    }

    @GetMapping
    public List<VoucherDto.VoucherResponse> findVouchers() {

        return voucherService.list().stream()
                .map(VoucherDto.VoucherResponse::from)
                .toList();
    }
}