package kdt.vouchermanagement.domain.voucher.controller;

import kdt.vouchermanagement.domain.voucher.domain.Voucher;
import kdt.vouchermanagement.domain.voucher.domain.VoucherType;
import kdt.vouchermanagement.domain.voucher.dto.VoucherRequest;
import kdt.vouchermanagement.domain.voucher.service.VoucherService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping
    public ResponseEntity<List> findVouchers() {
        List<Voucher> vouchers = voucherService.findVouchers();
        return ResponseEntity.status(HttpStatus.OK).body(vouchers);
    }

    @DeleteMapping("/{voucherId}")
    public ResponseEntity<Void> deleteVoucher(@PathVariable Long voucherId) {
        voucherService.deleteVoucher(voucherId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{voucherId}")
    public ResponseEntity<Voucher> findVoucher(@PathVariable Long voucherId) {
        Voucher voucher = voucherService.findVoucher(voucherId);
        return ResponseEntity.status(HttpStatus.OK).body(voucher);
    }

    @GetMapping("/category")
    public ResponseEntity<List> findVoucherByType(@RequestParam VoucherType type) {
        List<Voucher> vouchers = voucherService.findVouchers();
        List<Voucher> foundVouchers = vouchers.stream()
                .filter(v -> v.getVoucherType().equals(type))
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(foundVouchers);
    }

    @GetMapping("/date")
    public ResponseEntity<List> findVoucherByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<Voucher> vouchers = voucherService.findVouchers();
        List<Voucher> foundVouchers = vouchers.stream()
                .filter(v -> LocalDate.from(v.getCreatedAt()).equals(date))
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(foundVouchers);
    }
}
