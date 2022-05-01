package kdt.vouchermanagement.domain.voucher.controller;

import kdt.vouchermanagement.domain.voucher.domain.Voucher;
import kdt.vouchermanagement.domain.voucher.dto.VoucherRequest;
import kdt.vouchermanagement.domain.voucher.service.VoucherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return ResponseEntity.status(HttpStatus.CREATED).body(savedVoucher);
    }

    @GetMapping
    public ResponseEntity<List> findVouchers() {
        List<Voucher> vouchers = voucherService.findVouchers();
        return ResponseEntity.status(HttpStatus.OK).body(vouchers);
    }
}
