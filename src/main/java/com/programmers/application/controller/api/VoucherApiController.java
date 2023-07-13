package com.programmers.application.controller.api;

import com.programmers.application.dto.reponse.VoucherInfoResponse;
import com.programmers.application.dto.request.VoucherCreationRequest;
import com.programmers.application.service.VoucherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class VoucherApiController {

    private final VoucherService voucherService;

    public VoucherApiController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/api/v1/vouchers")
    public ResponseEntity<List<VoucherInfoResponse>> findVouchers() {
        List<VoucherInfoResponse> voucherList = voucherService.findVoucherList();
        return ResponseEntity.ok().body(voucherList);
    }

    @GetMapping("/api/v1/voucher/{voucherId}")
    public ResponseEntity<VoucherInfoResponse> findVoucherByVoucherId(@PathVariable UUID voucherId) {
        VoucherInfoResponse voucher = voucherService.findVoucherByVoucherId(voucherId);
        return ResponseEntity.ok().body(voucher);
    }

    @PostMapping("/api/v1/voucher")
    public ResponseEntity<Void> createVoucher(@RequestBody VoucherCreationRequest voucherCreationRequest) {
        voucherService.createVoucher(voucherCreationRequest);
        return ResponseEntity.noContent().build();
    }
}
