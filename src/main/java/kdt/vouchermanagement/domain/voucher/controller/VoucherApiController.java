package kdt.vouchermanagement.domain.voucher.controller;

import kdt.vouchermanagement.domain.voucher.domain.Voucher;
import kdt.vouchermanagement.domain.voucher.domain.VoucherType;
import kdt.vouchermanagement.domain.voucher.dto.VoucherRequest;
import kdt.vouchermanagement.domain.voucher.service.VoucherService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/vouchers")
public class VoucherApiController {

    private final VoucherService voucherService;

    public VoucherApiController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @PostMapping
    public ResponseEntity<Voucher> createVoucher(@RequestBody VoucherRequest request) {
        Voucher savedVoucher = voucherService.createVoucher(request.toDomain());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVoucher(@PathVariable Long id) {
        voucherService.deleteVoucher(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Voucher> findVoucher(@PathVariable Long id) {
        Voucher voucher = voucherService.findVoucher(id);
        return ResponseEntity.status(HttpStatus.OK).body(voucher);
    }

    @GetMapping
    public ResponseEntity<List> findVouchers(
            @RequestParam @Nullable VoucherType type,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @Nullable LocalDate date
    ) {
        if (type != null && date != null) {
            return ResponseEntity.status(HttpStatus.OK).body(voucherService.findVouchersByTypeAndDate(type, date));
        }

        if (type != null) {
            return ResponseEntity.status(HttpStatus.OK).body(voucherService.findVouchersByType(type));
        }

        if (date != null) {
            return ResponseEntity.status(HttpStatus.OK).body(voucherService.findVouchersByDate(date));
        }

        return ResponseEntity.status(HttpStatus.OK).body(voucherService.findVouchers());
    }
}