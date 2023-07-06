package org.programers.vouchermanagement.voucher.presentation;

import lombok.RequiredArgsConstructor;
import org.programers.vouchermanagement.voucher.application.VoucherService;
import org.programers.vouchermanagement.voucher.dto.request.VoucherCreationRequest;
import org.programers.vouchermanagement.voucher.dto.response.VoucherResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
