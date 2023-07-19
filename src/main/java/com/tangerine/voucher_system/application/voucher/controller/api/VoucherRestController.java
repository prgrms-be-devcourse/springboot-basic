package com.tangerine.voucher_system.application.voucher.controller.api;

import com.tangerine.voucher_system.application.voucher.controller.VoucherController;
import com.tangerine.voucher_system.application.voucher.controller.dto.CreateVoucherRequest;
import com.tangerine.voucher_system.application.voucher.controller.dto.UpdateVoucherRequest;
import com.tangerine.voucher_system.application.voucher.controller.dto.VoucherResponse;
import com.tangerine.voucher_system.application.voucher.controller.mapper.VoucherControllerMapper;
import com.tangerine.voucher_system.application.voucher.model.DiscountValue;
import com.tangerine.voucher_system.application.voucher.service.VoucherService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/vouchers")
public class VoucherRestController implements VoucherController {

    private final VoucherService voucherService;

    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @Override
    @GetMapping("/create")
    public ResponseEntity<VoucherResponse> createVoucher(@RequestBody CreateVoucherRequest request) {
        return ResponseEntity.ok(
                VoucherControllerMapper.INSTANCE.resultToResponse(
                        voucherService.createVoucher(VoucherControllerMapper.INSTANCE.requestToParam(request)))
        );
    }

    @Override
    @GetMapping("/update")
    public ResponseEntity<VoucherResponse> updateVoucher(@RequestBody UpdateVoucherRequest request) {
        return ResponseEntity.ok(
                VoucherControllerMapper.INSTANCE.resultToResponse(
                        voucherService.updateVoucher(VoucherControllerMapper.INSTANCE.requestToParam(request)))
        );
    }

    @Override
    @GetMapping("/all")
    public ResponseEntity<List<VoucherResponse>> voucherList() {
        return ResponseEntity.ok(
                voucherService.findVouchers()
                        .stream()
                        .map(VoucherControllerMapper.INSTANCE::resultToResponse)
                        .toList()
        );
    }

    @Override
    @GetMapping("/id/{voucherId}")
    public ResponseEntity<VoucherResponse> voucherById(@PathVariable UUID voucherId) {
        return ResponseEntity.ok(
                VoucherControllerMapper.INSTANCE.resultToResponse(voucherService.findVoucherById(voucherId))
        );
    }

    @Override
    @GetMapping("/created-date/{createdAt}")
    public ResponseEntity<List<VoucherResponse>> voucherByCreatedAt(@PathVariable LocalDate createdAt) {
        return ResponseEntity.ok(
                voucherService.findVoucherByCreatedAt(createdAt)
                        .stream()
                        .map(VoucherControllerMapper.INSTANCE::resultToResponse)
                        .toList()
        );
    }

    @Override
    @GetMapping("/delete/{voucherId}")
    public ResponseEntity<VoucherResponse> deleteVoucherById(@PathVariable UUID voucherId) {
        return ResponseEntity.ok(
                VoucherControllerMapper.INSTANCE.resultToResponse(voucherService.deleteVoucherById(voucherId))
        );
    }

}
