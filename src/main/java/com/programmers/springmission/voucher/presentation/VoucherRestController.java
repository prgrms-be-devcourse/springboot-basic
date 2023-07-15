package com.programmers.springmission.voucher.presentation;

import com.programmers.springmission.voucher.application.VoucherService;
import com.programmers.springmission.voucher.presentation.request.VoucherCreateRequest;
import com.programmers.springmission.voucher.presentation.request.VoucherUpdateRequest;
import com.programmers.springmission.voucher.presentation.response.VoucherResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/voucher")
@RequiredArgsConstructor
public class VoucherRestController {

    private final VoucherService voucherService;

    @PostMapping
    public ResponseEntity<VoucherResponse> createVoucher(@RequestBody VoucherCreateRequest voucherCreateRequest) {
        return ResponseEntity.ok(voucherService.createVoucher(voucherCreateRequest));
    }

    @GetMapping("/{voucherId}")
    public ResponseEntity<VoucherResponse> findVoucher(@PathVariable("voucherId") UUID voucherId) {
        return ResponseEntity.ok(voucherService.findOneVoucher(voucherId));
    }

    @GetMapping("/list")
    public ResponseEntity<List<VoucherResponse>> findAllVoucher() {
        return ResponseEntity.ok(voucherService.findAllVoucher());
    }

    @PutMapping("/amount/{voucherId}")
    public ResponseEntity<VoucherResponse> updateAmount(@PathVariable("voucherId") UUID voucherId,
                                                        @RequestBody VoucherUpdateRequest voucherUpdateRequest) {
        return ResponseEntity.ok(voucherService.updateAmount(voucherId, voucherUpdateRequest));
    }

    @PutMapping("/registration/{voucherId}/{customerId}")
    public ResponseEntity<VoucherResponse> updateCustomer(@PathVariable("voucherId") UUID voucherId,
                                                          @PathVariable("customerId") UUID customerId) {
        return ResponseEntity.ok(voucherService.updateCustomer(voucherId, customerId));
    }

    @DeleteMapping("/{voucherId}")
    public ResponseEntity<Void> deleteVoucher(@PathVariable("voucherId") UUID voucherId) {
        voucherService.deleteVoucher(voucherId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/list")
    public ResponseEntity<Void> deleteAllVoucher() {
        voucherService.deleteAllVoucher();
        return ResponseEntity.noContent().build();
    }
}
