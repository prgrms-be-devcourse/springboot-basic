package com.programmers.voucher.controller.voucher;

import com.programmers.voucher.controller.voucher.dto.VoucherAssignRequest;
import com.programmers.voucher.controller.voucher.dto.VoucherCreateRequest;
import com.programmers.voucher.model.voucher.Voucher;
import com.programmers.voucher.service.VoucherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<List<Voucher>> findAll(@RequestParam(required = false) String email) {
        List<Voucher> vouchers = voucherService.findAll(email);
        return ResponseEntity.ok(vouchers);
    }

    @GetMapping("/{voucherId}")
    public ResponseEntity<Voucher> findById(@PathVariable UUID voucherId) {
        Voucher voucher = voucherService.findById(voucherId);
        return ResponseEntity.ok(voucher);
    }

    @DeleteMapping("/delete/{voucherId}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID voucherId) {
        voucherService.deleteById(voucherId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteAll(@RequestParam(required = false) String email) {
        voucherService.deleteAll(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/assign")
    public ResponseEntity<Voucher> assign(@RequestBody VoucherAssignRequest voucherAssignRequest) {
        Voucher assigned = voucherService.assign(voucherAssignRequest.voucherId(), voucherAssignRequest.email());
        return ResponseEntity.ok(assigned);
    }
}
