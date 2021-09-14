package org.programmers.voucher.controller;

import org.programmers.voucher.model.*;
import org.programmers.voucher.service.VoucherJdbcService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class VoucherApiController {

    private final VoucherJdbcService voucherJdbcService;

    public VoucherApiController(VoucherJdbcService voucherJdbcService) {
        this.voucherJdbcService = voucherJdbcService;
    }

    @GetMapping(value = "/api/vouchers")
    @ResponseBody
    public List<VoucherBase> findVouchers() {
        return voucherJdbcService.findAllVouchers();
    }

    @GetMapping("/api/vouchers/type")
    public ResponseEntity<List<VoucherBase>> findVouchersByType(@RequestBody SearchVoucherTypeRequest searchVoucherTypeRequest) {

        var voucherList = voucherJdbcService.findByVouchersType(searchVoucherTypeRequest.getVoucherType());

        return Optional.ofNullable(voucherList)
                .map(ResponseEntity.ok()::body)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @GetMapping("/api/vouchers/{voucherId}")
    public ResponseEntity<VoucherBase> findVoucher(@PathVariable("voucherId") UUID voucherId) {
        var voucher = voucherJdbcService.findById(voucherId);
        return Optional.ofNullable(voucher)
                .map(ResponseEntity.ok()::body)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @PostMapping("/api/voucher")
    public ResponseEntity<VoucherBase> saveVoucher(@RequestBody CreateVoucherRequest createVoucherRequest) {
        var voucher = voucherJdbcService.save(createVoucherRequest);

        return Optional.ofNullable(voucher)
                .map(ResponseEntity.ok()::body)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @DeleteMapping("/api/voucher/{voucherId}")
    @ResponseBody
    public ResponseEntity<VoucherBase> deleteVoucher(@PathVariable("voucherId") UUID voucherId) {
        voucherJdbcService.deleteByVoucherId(voucherId);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
