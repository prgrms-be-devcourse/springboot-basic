package com.tangerine.voucher_system.application.voucher.controller;

import com.tangerine.voucher_system.application.voucher.controller.dto.CreateVoucherRequest;
import com.tangerine.voucher_system.application.voucher.controller.dto.UpdateVoucherRequest;
import com.tangerine.voucher_system.application.voucher.controller.dto.VoucherResponse;
import com.tangerine.voucher_system.application.voucher.controller.mapper.VoucherControllerMapper;
import com.tangerine.voucher_system.application.voucher.service.VoucherService;
import org.springframework.http.MediaType;
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

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VoucherResponse> addVoucher(@RequestBody CreateVoucherRequest request) {
        return ResponseEntity.ok(
                VoucherControllerMapper.INSTANCE.resultToResponse(
                        voucherService.createVoucher(VoucherControllerMapper.INSTANCE.requestToParam(request)))
        );
    }

    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VoucherResponse> modifyVoucher(@RequestBody UpdateVoucherRequest request) {
        return ResponseEntity.ok(
                VoucherControllerMapper.INSTANCE.resultToResponse(
                        voucherService.updateVoucher(VoucherControllerMapper.INSTANCE.requestToParam(request)))
        );
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<VoucherResponse>> voucherList() {
        return ResponseEntity.ok(
                VoucherControllerMapper.INSTANCE.resultsToResponses(voucherService.findVouchers())
        );
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VoucherResponse> voucherById(@PathVariable("id") UUID voucherId) {
        return ResponseEntity.ok(
                VoucherControllerMapper.INSTANCE.resultToResponse(voucherService.findVoucherById(voucherId))
        );
    }

    @GetMapping(path = "/created-at/{createdAt}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<VoucherResponse>> voucherByCreatedAt(@PathVariable("createdAt") LocalDate createdAt) {
        return ResponseEntity.ok(
                VoucherControllerMapper.INSTANCE.resultsToResponses(voucherService.findVoucherByCreatedAt(createdAt))
        );
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VoucherResponse> deleteVoucherById(@PathVariable("id") UUID voucherId) {
        return ResponseEntity.ok(
                VoucherControllerMapper.INSTANCE.resultToResponse(voucherService.deleteVoucherById(voucherId))
        );
    }

}
