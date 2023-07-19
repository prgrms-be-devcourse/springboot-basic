package com.example.demo.voucher.presentation.controller;

import com.example.demo.voucher.application.VoucherService;
import com.example.demo.voucher.domain.Voucher;
import com.example.demo.voucher.presentation.controller.dto.CreateVoucherForm;
import org.springframework.http.HttpStatus;
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

    @GetMapping
    public ResponseEntity<List<Voucher>> findAll() {
        return new ResponseEntity<>(voucherService.listVouchers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Voucher> findById(@PathVariable("id") String id) {
        return new ResponseEntity<>(voucherService.getVoucher(UUID.fromString(id)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createVoucher(@RequestBody CreateVoucherForm voucherForm) {
        voucherService.createVoucher(voucherForm.voucherType(), voucherForm.value());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        voucherService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
