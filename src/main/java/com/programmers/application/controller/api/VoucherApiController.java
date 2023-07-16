package com.programmers.application.controller.api;

import com.programmers.application.dto.reponse.VoucherInfoResponse;
import com.programmers.application.dto.request.VoucherCreationRequest;
import com.programmers.application.service.VoucherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/api")
public class VoucherApiController {

    private final VoucherService voucherService;

    public VoucherApiController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/v1/vouchers")
    public ResponseEntity<List<VoucherInfoResponse>> findVouchers() {
        List<VoucherInfoResponse> voucherList = voucherService.findVoucherList();
        return ResponseEntity.ok().body(voucherList);
    }

    @GetMapping("/v1/voucher/{voucherId}")
    public ResponseEntity<VoucherInfoResponse> findVoucherByVoucherId(@PathVariable UUID voucherId) {
        VoucherInfoResponse voucher = voucherService.findVoucherByVoucherId(voucherId);
        return ResponseEntity.ok().body(voucher);
    }

    @PostMapping("/v1/voucher")
    public ResponseEntity<VoucherInfoResponse> createVoucher(@RequestBody VoucherCreationRequest voucherCreationRequest) {
        UUID voucherId = voucherService.createVoucher(voucherCreationRequest);
        VoucherInfoResponse voucher = voucherService.findVoucherByVoucherId(voucherId);
        return ResponseEntity.status(HttpStatus.CREATED).body(voucher);
    }
}
