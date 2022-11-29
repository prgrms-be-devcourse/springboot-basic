package org.prgrms.voucherapplication.domain.voucher.controller;

import org.prgrms.voucherapplication.domain.voucher.controller.dto.CreateVoucherRequest;
import org.prgrms.voucherapplication.domain.voucher.entity.VoucherType;
import org.prgrms.voucherapplication.domain.voucher.service.VoucherService;
import org.prgrms.voucherapplication.domain.voucher.entity.Voucher;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/vouchers")
public class VoucherApiController {
    private final VoucherService voucherService;

    public VoucherApiController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/{voucherId}")
    public ResponseEntity<Voucher> findVoucherById(@PathVariable UUID voucherId) {
        Optional<Voucher> voucher = voucherService.findById(voucherId);
        return voucher.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> saveVoucher(@RequestBody CreateVoucherRequest createVoucherRequest) {
        VoucherType voucherType = createVoucherRequest.getVoucherType();
        Voucher voucher = voucherType.createVoucher(UUID.randomUUID(), createVoucherRequest.getDiscount(), LocalDateTime.now());
        voucherService.create(voucher);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{voucherId}")
    public ResponseEntity<HttpStatus> deleteVoucher(@PathVariable UUID voucherId) {
        voucherService.deleteById(voucherId);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public ResponseEntity<List<Voucher>> findAllByConditions(
            @RequestParam("voucherType") @Nullable String voucherType,
            @RequestParam("createdAt") @Nullable @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime createdAt) {
        if (voucherType != null) {
            VoucherType type = VoucherType.of(voucherType);

            if (createdAt != null) {
                return ResponseEntity.ok().body(voucherService.findByTypeAndCreatedAt(type, createdAt));
            }

            return ResponseEntity.ok().body(voucherService.findByType(type));
        }

        if (createdAt != null) {
            return ResponseEntity.ok().body(voucherService.findByCreatedAtAfter(createdAt));
        }

        return ResponseEntity.ok().body(voucherService.findAll());
    }
}
