package me.kimihiqq.vouchermanagement.controller;

import me.kimihiqq.vouchermanagement.domain.voucher.Voucher;
import me.kimihiqq.vouchermanagement.domain.voucher.dto.VoucherDto;
import me.kimihiqq.vouchermanagement.domain.voucher.service.VoucherService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/vouchers")
public class VoucherApiController {

    private final VoucherService voucherService;

    public VoucherApiController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    public List<Voucher> getAllVouchers() {
        return voucherService.listVouchers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Voucher> getVoucherById(@PathVariable UUID id) {
        Optional<Voucher> voucherOptional = voucherService.findVoucherById(id);
        return voucherOptional
                .map(voucher -> ResponseEntity.ok(voucher))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Voucher createVoucher(@RequestBody VoucherDto voucherDto) {
        return voucherService.createVoucher(voucherDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVoucher(@PathVariable UUID id) {
        voucherService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<Voucher> searchVouchersByCreationDateTime(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return voucherService.listVouchersByCreationDateTimeBetween(start, end);
    }

}

