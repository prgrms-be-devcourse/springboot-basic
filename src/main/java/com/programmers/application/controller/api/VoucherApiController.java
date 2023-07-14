package com.programmers.application.controller.api;

import com.programmers.application.dto.reponse.VoucherInfoResponse;
import com.programmers.application.dto.request.VoucherCreationRequest;
import com.programmers.application.service.VoucherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

@Controller
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
    public ResponseEntity<VoucherInfoResponse> createVoucher(@RequestBody VoucherCreationRequest voucherCreationRequest) {
        UUID voucherId = voucherService.createVoucher(voucherCreationRequest);
        VoucherInfoResponse voucher = voucherService.findVoucherByVoucherId(voucherId);
        return ResponseEntity.status(HttpStatus.CREATED).body(voucher);
    }
}
