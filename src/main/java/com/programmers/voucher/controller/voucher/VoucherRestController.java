package com.programmers.voucher.controller.voucher;

import com.programmers.voucher.controller.voucher.dto.VoucherAssignRequest;
import com.programmers.voucher.controller.voucher.dto.VoucherCreateRequest;
import com.programmers.voucher.model.voucher.Voucher;
import com.programmers.voucher.service.VoucherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/vouchers")
public class VoucherRestController {
    private final VoucherService voucherService;

    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @PostMapping("/add")
    public ResponseEntity<Voucher> create(@RequestBody VoucherCreateRequest voucherCreateRequest) {
        Voucher newVoucher = voucherService.create(voucherCreateRequest);
        return ResponseEntity.ok(newVoucher);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Voucher>> findAll() {
        List<Voucher> vouchers = voucherService.findAll();
        return ResponseEntity.ok(vouchers);
    }

    @GetMapping("/{voucherId}")
    public ResponseEntity<Voucher> findById(@PathVariable UUID voucherId) {
        Voucher voucher = voucherService.findById(voucherId);
        return ResponseEntity.ok(voucher);
    }

    @GetMapping
    public ResponseEntity<List<Voucher>> findAllByCustomer(@RequestParam String email) {
        List<Voucher> vouchers = voucherService.findAllByCustomer(email);
        return ResponseEntity.ok(vouchers);
    }

    @DeleteMapping("/{voucherId}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID voucherId) {
        voucherService.deleteById(voucherId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        voucherService.deleteAll();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteByCustomer(@RequestParam String email) {
        voucherService.deleteByCustomer(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/assign")
    public ResponseEntity<Voucher> assign(@RequestBody VoucherAssignRequest voucherAssignRequest) {
        Voucher assigned = voucherService.assign(voucherAssignRequest.voucherId(), voucherAssignRequest.email());
        return ResponseEntity.ok(assigned);
    }
}
