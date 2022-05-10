package org.programmers.kdtspring.controller.api;

import org.programmers.kdtspring.dto.CreateVoucherRequest;
import org.programmers.kdtspring.entity.voucher.Voucher;
import org.programmers.kdtspring.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/vouchers")
public class VoucherRestController {

    private final Logger log = LoggerFactory.getLogger(VoucherRestController.class);

    private final VoucherService voucherService;

    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    public List<Voucher> findAllVouchers() {
        return voucherService.getVouchers();
    }

    @GetMapping("/{voucherId}")
    public ResponseEntity<Voucher> findVoucher(@PathVariable("voucherId") UUID voucherId) {
        Optional<Voucher> voucher = voucherService.getVoucher(voucherId);
        return voucher.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("{voucherType}")
    public List<Voucher> getVoucherByType(@PathVariable String voucherType) {
        return voucherService.getVouchersByType(voucherType);
    }

    @PostMapping
    public ResponseEntity<Voucher> createVoucher(@RequestBody CreateVoucherRequest createVoucherRequest) {
        Optional<Voucher> voucher = createVoucherRequest.getVoucherType().toString().equalsIgnoreCase("FixedAmountVoucher") ?
                voucherService.createVoucher(createVoucherRequest.getVoucherType().toString(), createVoucherRequest.getAmount()) :
                voucherService.createVoucher(createVoucherRequest.getVoucherType().toString(), createVoucherRequest.getPercent());
        return voucher.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{voucherId}")
    public void deleteSingleVoucher(@PathVariable("voucherId") UUID voucherId) {
        voucherService.removeVoucher(voucherId);
    }

    @DeleteMapping
    public void deleteAllVoucher() {
        voucherService.removeAllVoucher();
    }
}