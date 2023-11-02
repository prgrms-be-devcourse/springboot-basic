package org.prgrms.kdtspringdemo.voucher.controller;

import org.prgrms.kdtspringdemo.voucher.domain.Voucher;
import org.prgrms.kdtspringdemo.voucher.domain.dto.VoucherViewDto;
import org.prgrms.kdtspringdemo.voucher.service.VoucherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/voucher")
public class VoucherRestController {
    private final VoucherService voucherService;

    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/vouchers")
    ResponseEntity<List<VoucherViewDto>> showAllVouchers() {
        final List<VoucherViewDto> vouchers = voucherService.findAll().stream()
                .map(VoucherViewDto::new).toList();
        return new ResponseEntity<>(vouchers, HttpStatus.OK);

    }

}
