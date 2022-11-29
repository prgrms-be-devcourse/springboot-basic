package org.prgrms.voucherapplication.domain.voucher.controller;

import org.prgrms.voucherapplication.domain.voucher.controller.dto.CreateVoucherRequest;
import org.prgrms.voucherapplication.domain.voucher.entity.VoucherType;
import org.prgrms.voucherapplication.domain.voucher.service.VoucherService;
import org.prgrms.voucherapplication.domain.voucher.entity.Voucher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class VoucherApiController {
    private final VoucherService voucherService;

    public VoucherApiController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/api/v1/vouchers")
    public List<Voucher> findVouchers() {
        return voucherService.findAll();
    }

    @GetMapping("/api/v1/vouchers/{voucherId}")
    public ResponseEntity<Voucher> findVoucherById(@PathVariable UUID voucherId) {
        Optional<Voucher> voucher = voucherService.findById(voucherId);
        return voucher.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/api/v1/vouchers")
    public ResponseEntity<HttpStatus> saveVoucher(@RequestBody CreateVoucherRequest createVoucherRequest) {
        VoucherType voucherType = createVoucherRequest.getVoucherType();
        Voucher voucher = voucherType.createVoucher(UUID.randomUUID(), createVoucherRequest.getDiscount(), LocalDateTime.now());
        voucherService.create(voucher);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/v1/vouchers/{voucherId}")
    public ResponseEntity<HttpStatus> deleteVoucher(@PathVariable UUID voucherId) {
        voucherService.deleteById(voucherId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/v1/vouchers/type/{voucherType}")
    public ResponseEntity<List<Voucher>> findByVoucherType(@PathVariable String voucherType) {
        VoucherType type = VoucherType.of(voucherType);
        List<Voucher> byType = voucherService.findByType(type);
        return ResponseEntity.ok().body(byType);
    }
}
