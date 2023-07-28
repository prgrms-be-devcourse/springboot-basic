package com.tangerine.voucher_system.application.voucher.controller;

import com.tangerine.voucher_system.application.voucher.controller.dto.CreateVoucherRequest;
import com.tangerine.voucher_system.application.voucher.controller.dto.UpdateVoucherRequest;
import com.tangerine.voucher_system.application.voucher.controller.dto.VoucherResponse;
import com.tangerine.voucher_system.application.voucher.controller.mapper.VoucherControllerMapper;
import com.tangerine.voucher_system.application.voucher.service.VoucherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/vouchers")
public class VoucherRestController {

    private final VoucherService voucherService;

    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @PostMapping("")
    public ResponseEntity<VoucherResponse> createVoucher(@RequestBody CreateVoucherRequest request) {
        return ResponseEntity.ok(
                VoucherControllerMapper.INSTANCE.resultToResponse(
                        voucherService.createVoucher(VoucherControllerMapper.INSTANCE.requestToParam(request)))
        );
    }

    @PatchMapping("")
    public ResponseEntity<VoucherResponse> updateVoucher(@RequestBody UpdateVoucherRequest request) {
        return ResponseEntity.ok(
                VoucherControllerMapper.INSTANCE.resultToResponse(
                        voucherService.updateVoucher(VoucherControllerMapper.INSTANCE.requestToParam(request)))
        );
    }

    @GetMapping("")
    public ResponseEntity<List<VoucherResponse>> voucherList() {
        return ResponseEntity.ok(
                VoucherControllerMapper.INSTANCE.resultsToResponses(voucherService.findVouchers())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<VoucherResponse> voucherById(@PathVariable("id") UUID voucherId) {
        return ResponseEntity.ok(
                VoucherControllerMapper.INSTANCE.resultToResponse(voucherService.findVoucherById(voucherId))
        );
    }

    @GetMapping("/by-created-at")
    public ResponseEntity<List<VoucherResponse>> voucherByCreatedAt(
            @RequestParam(name = "createdAt") LocalDate createdAt) {
        return ResponseEntity.ok(
                VoucherControllerMapper.INSTANCE.resultsToResponses(voucherService.findVoucherByCreatedAt(createdAt))
        );
    }

    @DeleteMapping("")
    public ResponseEntity<VoucherResponse> deleteVoucherById(@RequestParam(name = "id") UUID voucherId) {
        return ResponseEntity.ok(
                VoucherControllerMapper.INSTANCE.resultToResponse(voucherService.deleteVoucherById(voucherId))
        );
    }

}
