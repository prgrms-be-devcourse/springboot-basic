package org.programers.vouchermanagement.voucher.presentation;

import lombok.RequiredArgsConstructor;
import org.programers.vouchermanagement.voucher.application.VoucherService;
import org.programers.vouchermanagement.voucher.dto.request.VoucherCreationRequest;
import org.programers.vouchermanagement.voucher.dto.response.VoucherResponse;
import org.programers.vouchermanagement.voucher.dto.response.VouchersResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/vouchers")
@RequiredArgsConstructor
@RestController
public class VoucherApiController {

    private final VoucherService voucherService;

    @PostMapping
    public ResponseEntity<VoucherResponse> save(@RequestBody VoucherCreationRequest request) {
        VoucherResponse response = voucherService.save(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VoucherResponse> findById(@PathVariable UUID id) {
        VoucherResponse response = voucherService.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<VouchersResponse> findAll() {
        VouchersResponse response = voucherService.findAll();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        voucherService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
