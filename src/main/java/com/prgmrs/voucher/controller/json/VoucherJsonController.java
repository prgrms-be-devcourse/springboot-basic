package com.prgmrs.voucher.controller.json;

import com.prgmrs.voucher.dto.request.VoucherIdRequest;
import com.prgmrs.voucher.dto.request.VoucherRequest;
import com.prgmrs.voucher.dto.request.VoucherSearchRequest;
import com.prgmrs.voucher.dto.response.RemoveResponse;
import com.prgmrs.voucher.dto.response.VoucherListResponse;
import com.prgmrs.voucher.dto.response.VoucherResponse;
import com.prgmrs.voucher.service.VoucherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/json/v1")
public class VoucherJsonController {

    private final VoucherService voucherService;

    public VoucherJsonController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/vouchers")
    public ResponseEntity<VoucherListResponse> findAll() {
        return ResponseEntity.ok(voucherService.findAll());
    }

    @GetMapping("/vouchers/search")
    public ResponseEntity<VoucherListResponse> findByCreationTimeAndDiscountType(VoucherSearchRequest voucherSearchRequest) {
        return ResponseEntity.ok(voucherService.findByCreationTimeAndDiscountType(voucherSearchRequest));
    }

    @PostMapping("/vouchers")
    public ResponseEntity<VoucherResponse> createVoucher(@RequestBody VoucherRequest voucherRequest) {
        return ResponseEntity.ok(voucherService.createVoucher(voucherRequest));
    }

    @DeleteMapping("/vouchers/{voucherId}")
    public ResponseEntity<RemoveResponse> removeVoucher(@PathVariable("voucherId") VoucherIdRequest voucherIdRequest) {
        return ResponseEntity.ok(voucherService.removeVoucher(voucherIdRequest));
    }

    @GetMapping("/vouchers/{voucherId}")
    public ResponseEntity<VoucherResponse> findById(@PathVariable("voucherId") VoucherIdRequest voucherIdRequest) {
        return ResponseEntity.ok(voucherService.findById(voucherIdRequest));
    }
}
