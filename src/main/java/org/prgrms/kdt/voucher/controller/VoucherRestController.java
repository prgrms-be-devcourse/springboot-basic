package org.prgrms.kdt.voucher.controller;

import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/vouchers", produces = MediaType.APPLICATION_JSON_VALUE)    // xml로 변경 가능
public class VoucherRestController {

    private final VoucherService voucherService;

    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("")
    public ResponseEntity<List<Voucher>> getAllVouchers() {
        List<Voucher> vouchers = voucherService.getAllVouchers();
        if (vouchers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(vouchers, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createVoucher(@RequestParam String type, @RequestParam int amount) {
        if (type.equals("fixed")) {
            voucherService.createFixedAmountVoucher(amount);
            return new ResponseEntity<>("Fixed amount voucher created successfully", HttpStatus.CREATED);
        } else if (type.equals("percent")) {
            voucherService.createPercentDiscountVoucher(amount);
            return new ResponseEntity<>("Percent discount voucher created successfully", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Invalid voucher type", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{voucherId}")
    public ResponseEntity<String> removeVoucher(@PathVariable UUID voucherId) {
        voucherService.removeVoucherById(voucherId);
        return new ResponseEntity<>("Voucher removed successfully", HttpStatus.OK);
    }

    @GetMapping("/{voucherId}")
    public ResponseEntity<Voucher> getVoucherById(@PathVariable UUID voucherId) {
        Voucher voucher = voucherService.getVoucher(voucherId);
        return new ResponseEntity<>(voucher, HttpStatus.OK);
    }

    @GetMapping("/criteria")
    public ResponseEntity<List<Voucher>> getVouchersByCriteria(@RequestParam String startDate, @RequestParam String endDate, @RequestParam String voucherType) {
        LocalDateTime start = LocalDateTime.parse(startDate);
        LocalDateTime end = LocalDateTime.parse(endDate);
        List<Voucher> vouchers = voucherService.getVouchersByCriteria(start, end, voucherType);
        if (vouchers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(vouchers, HttpStatus.OK);
    }
}
