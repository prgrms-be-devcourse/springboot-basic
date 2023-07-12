package com.programmers.vouchermanagement.voucher.presentation;

import com.programmers.vouchermanagement.voucher.application.VoucherService;
import com.programmers.vouchermanagement.voucher.dto.request.VoucherCreationRequest;
import com.programmers.vouchermanagement.voucher.dto.request.VoucherUpdateRequest;
import com.programmers.vouchermanagement.voucher.dto.response.VoucherResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v3/api/vouchers")
@RequiredArgsConstructor
public class RestApiVoucherController {

    private final VoucherService voucherService;

    @PostMapping
    public ResponseEntity<VoucherResponse> createVoucher(@RequestBody VoucherCreationRequest request) {
        VoucherResponse response = voucherService.createVoucher(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<VoucherResponse>> getVouchers() {
        List<VoucherResponse> responses = voucherService.getVouchers();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{voucherId}")
    public ResponseEntity<VoucherResponse> getVoucher(@PathVariable UUID voucherId) {
        VoucherResponse response = voucherService.getVoucher(voucherId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{voucherId}")
    public ResponseEntity<Void> updateVoucher(@RequestBody VoucherUpdateRequest request) {
        voucherService.updateVoucher(request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{voucherId}")
    public ResponseEntity<Void> deleteVoucher(@PathVariable UUID voucherId) {
        voucherService.deleteVoucher(voucherId);
        return ResponseEntity.noContent().build();
    }
}
