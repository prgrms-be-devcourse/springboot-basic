package org.prgrms.springorder.domain.voucher.api;

import org.prgrms.springorder.domain.voucher.service.VoucherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/vouchers")
@RestController
public class VoucherRestController {

    private final VoucherService voucherService;

    public VoucherRestController(
        VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    public ResponseEntity<?> getAllVouchers() {
        return ResponseEntity.ok(voucherService.findAll());
    }

}
