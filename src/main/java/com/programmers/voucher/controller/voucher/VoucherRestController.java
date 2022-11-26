package com.programmers.voucher.controller.voucher;

import com.programmers.voucher.controller.voucher.dto.VoucherAssignRequest;
import com.programmers.voucher.model.voucher.Voucher;
import com.programmers.voucher.service.VoucherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/")
public class VoucherRestController {
    private final VoucherService voucherService;

    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @DeleteMapping("voucher/{voucherId}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID voucherId) {
        voucherService.deleteById(voucherId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("voucher")
    public ResponseEntity<Void> deleteAll() {
        voucherService.deleteAll();
        return ResponseEntity.ok().build();
    }

    @PostMapping("voucher")
    public ResponseEntity<Voucher> assign(VoucherAssignRequest voucherAssignRequest) {
        Voucher assigned = voucherService.assign(voucherAssignRequest.voucherId(), voucherAssignRequest.email());
        return ResponseEntity.ok(assigned);
    }
}
