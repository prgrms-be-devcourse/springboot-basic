package com.programmers.vouchermanagement.controller.api;

import com.programmers.vouchermanagement.dto.voucher.request.CreateVoucherRequestDto;
import com.programmers.vouchermanagement.dto.voucher.request.GetVouchersRequestDto;
import com.programmers.vouchermanagement.dto.voucher.request.UpdateVoucherRequestDto;
import com.programmers.vouchermanagement.dto.voucher.response.VoucherResponseDto;
import com.programmers.vouchermanagement.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/vouchers")
@RequiredArgsConstructor
public class VoucherApiController {

    private final VoucherService voucherService;

    @PostMapping()
    public ResponseEntity<UUID> createVoucher(@RequestBody CreateVoucherRequestDto request) {
        UUID id = voucherService.createVoucher(request);
        return ResponseEntity.created(URI.create("/vouchers/" + id.toString())).body(id);
    }

    @GetMapping()
    public ResponseEntity<List<VoucherResponseDto>> getVouchers(@RequestBody GetVouchersRequestDto request) {
        return ResponseEntity.ok(voucherService.getVouchers(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VoucherResponseDto> getVoucher(@PathVariable UUID id) {
        return ResponseEntity.ok(voucherService.getVoucher(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateVoucher(@PathVariable UUID id, @RequestBody UpdateVoucherRequestDto request) {
        voucherService.updateVoucher(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVoucher(@PathVariable UUID id) {
        voucherService.deleteVoucher(id);
        return ResponseEntity.noContent().build();
    }
}
