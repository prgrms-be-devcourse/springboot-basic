package com.prgrms.springbasic.domain.voucher.controller;

import com.prgrms.springbasic.domain.voucher.dto.CreateVoucherRequest;
import com.prgrms.springbasic.domain.voucher.dto.UpdateVoucherRequest;
import com.prgrms.springbasic.domain.voucher.dto.VoucherResponse;
import com.prgrms.springbasic.domain.voucher.service.VoucherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/vouchers")
public class VoucherApiController {

    private final VoucherService voucherService;

    public VoucherApiController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    public ResponseEntity<List<VoucherResponse>> findAll() {
        return ResponseEntity.ok(voucherService.findAll());
    }

    @GetMapping("/{voucherId}")
    public ResponseEntity<VoucherResponse> findById(@PathVariable String voucherId) {
        VoucherResponse voucher = voucherService.findById(UUID.fromString(voucherId));
        return ResponseEntity.ok(voucher);
    }

    @PostMapping
    public ResponseEntity<VoucherResponse> create(@RequestBody CreateVoucherRequest request) {
        VoucherResponse voucher = voucherService.saveVoucher(request);
        return ResponseEntity.ok(voucher);
    }

    @PatchMapping
    public ResponseEntity<String> update(@RequestBody UpdateVoucherRequest request) {
        voucherService.updateVoucher(request);
        return ResponseEntity.ok("Voucher value updated to " + request.discountValue());
    }

    @DeleteMapping("/{voucherId}")
    public ResponseEntity<String> delete(@PathVariable String voucherId) {
        voucherService.deleteById(UUID.fromString(voucherId));
        return ResponseEntity.ok("Voucher deleted");
    }
}
