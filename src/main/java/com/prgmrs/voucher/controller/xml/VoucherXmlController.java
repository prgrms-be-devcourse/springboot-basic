package com.prgmrs.voucher.controller.xml;

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
@RequestMapping("/api/xml/v1")
public class VoucherXmlController {
    private final VoucherService voucherService;

    public VoucherXmlController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping(value = "/vouchers", produces = "application/xml")
    public ResponseEntity<VoucherListResponse> findAll() {
        return ResponseEntity.ok(voucherService.findAll());
    }

    @GetMapping(value = "/vouchers/search", produces = "application/xml")
    public ResponseEntity<VoucherListResponse> findByCreationTimeAndDiscountType(VoucherSearchRequest voucherSearchRequest) {
        return ResponseEntity.ok(voucherService.findByCreationTimeAndDiscountType(voucherSearchRequest));
    }

    @PostMapping(value = "/vouchers", consumes = "application/xml", produces = "application/xml")
    public ResponseEntity<VoucherResponse> createVoucher(@RequestBody VoucherRequest voucherRequest) {
        System.out.println(voucherRequest); // or use a logger
        return ResponseEntity.ok(voucherService.createVoucher(voucherRequest));
    }

    @DeleteMapping(value = "/vouchers/{voucherId}", produces = "application/xml")
    public ResponseEntity<RemoveResponse> removeVoucher(@PathVariable("voucherId") VoucherIdRequest voucherIdRequest) {
        return ResponseEntity.ok(voucherService.removeVoucher(voucherIdRequest));
    }

    @GetMapping(value = "/vouchers/{voucherId}", produces = "application/xml")
    public ResponseEntity<VoucherResponse> findById(@PathVariable("voucherId") VoucherIdRequest voucherIdRequest) {
        return ResponseEntity.ok(voucherService.findById(voucherIdRequest));
    }
}
