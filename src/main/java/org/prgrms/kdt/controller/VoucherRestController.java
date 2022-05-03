package org.prgrms.kdt.controller;

import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.model.voucher.VoucherMap;
import org.prgrms.kdt.model.voucher.VoucherType;
import org.prgrms.kdt.model.voucher.Vouchers;
import org.prgrms.kdt.service.VoucherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
public class VoucherRestController {
    private final VoucherService voucherService;


    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/api/v1/vouchers")
    @ResponseBody
    public ResponseEntity<Map<UUID, Voucher>> getVoucherList() {
        VoucherMap voucherList = voucherService.getVoucherList();
        return ResponseEntity.ok().body(voucherList.getVouchers());
    }

    @GetMapping("/api/v1/voucher/id/{voucherId}")
    @ResponseBody
    public ResponseEntity<Voucher> getVoucherByID(@PathVariable String voucherId) {
        Optional<Voucher> voucher = voucherService.getVoucherById(UUID.fromString(voucherId));
        return ResponseEntity.of(voucher);
    }

    @DeleteMapping("/api/v1/voucher/id/{voucherId}")
    @ResponseBody
    public ResponseEntity<Void> deleteVoucherById(@PathVariable String voucherId) {
        voucherService.deleteVoucherById(UUID.fromString(voucherId));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/v1/voucher/new/{voucherType}/{discountAmount}")
    @ResponseBody
    public ResponseEntity<Voucher> createVoucher(@PathVariable long discountAmount, @PathVariable VoucherType voucherType) {
        Voucher newVoucher = voucherService.createVoucher(UUID.randomUUID(), voucherType.getTypeNumber(), discountAmount);
        return ResponseEntity.ok().body(newVoucher);
    }

    @GetMapping("/api/v1/vouchers/voucherType/{voucherType}")
    @ResponseBody
    public ResponseEntity<List<Voucher>> getVouchersByVoucherType(@PathVariable VoucherType voucherType) {
        Vouchers vouchers = voucherService.getVoucherListByVoucherType(voucherType.getTypeNumber());
        return ResponseEntity.ok().body(vouchers.getVouchers());
    }

    @GetMapping("/api/v1/voucher/date/{fromDate}/{toDate}")
    @ResponseBody
    public ResponseEntity<List<Voucher>> createVoucher(@PathVariable String fromDate, @PathVariable String toDate) {
        Vouchers vouchers = voucherService.getVoucherListByCreatedFromToDate(fromDate, toDate);
        return ResponseEntity.ok().body(vouchers.getVouchers());
    }
}
