package org.programmers.springbootbasic.voucher.controller;

import org.programmers.springbootbasic.voucher.model.Voucher;
import org.programmers.springbootbasic.voucher.model.VoucherType;
import org.programmers.springbootbasic.voucher.service.VoucherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
public class RestVoucherController {

    private final VoucherService voucherService;

    public RestVoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @PostMapping("/api/v1/products")
    public Voucher createVoucher(@RequestBody CreateVoucherRequest voucherRequest) {
        return voucherService.createVoucher(
                VoucherType.valueOf(voucherRequest.voucherType()),
                UUID.randomUUID(),
                voucherRequest.value(),
                LocalDateTime.now()
        );
    }

    @GetMapping("/api/v1/vouchers")
    public List<Voucher> findVouchers() {
        return voucherService.getVoucherList();
    }

    @GetMapping("/api/v1/vouchers/{voucherId}")
    public ResponseEntity<Voucher> findVoucher(@PathVariable("voucherId") UUID voucherId) {
        var optionalVoucher = voucherService.getVoucher(voucherId);
        return optionalVoucher.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/api/v1/vouchers/{voucherId}")
    public Voucher updateVoucher(@RequestBody UpdateVoucherRequest updateVoucherRequest) {
        return voucherService.updateVoucher(updateVoucherRequest.voucherId(), updateVoucherRequest.value());
    }

    @DeleteMapping("/api/v1/vouchers/{voucherId}")
    public void deleteVoucher(DeleteVoucherRequest deleteVoucherRequest) {
        voucherService.deleteVoucher(deleteVoucherRequest.voucherId());
    }
}
