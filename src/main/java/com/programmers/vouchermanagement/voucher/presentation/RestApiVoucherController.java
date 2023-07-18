package com.programmers.vouchermanagement.voucher.presentation;

import com.programmers.vouchermanagement.voucher.application.VoucherService;
import com.programmers.vouchermanagement.voucher.dto.request.VoucherCreationRequest;
import com.programmers.vouchermanagement.voucher.dto.request.VoucherUpdateRequest;
import com.programmers.vouchermanagement.voucher.dto.response.VoucherResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v3/vouchers")
@RequiredArgsConstructor
public class RestApiVoucherController {

    private final VoucherService voucherService;

    @PostMapping
    public VoucherResponse createVoucher(@RequestBody VoucherCreationRequest request) {
        log.info("{}", request);
        return voucherService.createVoucher(request);
    }

    @GetMapping
    public List<VoucherResponse> getVouchers() {
        return voucherService.getVouchers();
    }

    @GetMapping("/{voucherId}")
    public VoucherResponse getVoucher(@PathVariable UUID voucherId) {
        log.info("voucherId={}", voucherId);
        return voucherService.getVoucher(voucherId);
    }

    @PostMapping("/{voucherId}")
    public boolean updateVoucher(@PathVariable UUID voucherId, @RequestBody VoucherUpdateRequest request) {
        log.info("voucherId={}, {}", voucherId, request);
        voucherService.updateVoucher(voucherId, request);
        return true;
    }

    @DeleteMapping("/{voucherId}")
    public boolean deleteVoucher(@PathVariable UUID voucherId) {
        log.info("voucherId={}", voucherId);
        voucherService.deleteVoucher(voucherId);
        return true;
    }
}
