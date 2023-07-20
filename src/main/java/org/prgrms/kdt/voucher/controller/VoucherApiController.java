package org.prgrms.kdt.voucher.controller;

import org.prgrms.kdt.voucher.controller.dto.ControllerCreateVoucherRequest;
import org.prgrms.kdt.voucher.controller.mapper.ControllerVoucherMapper;
import org.prgrms.kdt.voucher.domain.VoucherType;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.prgrms.kdt.voucher.service.dto.VoucherDetailResponse;
import org.prgrms.kdt.voucher.service.dto.VoucherResponse;
import org.prgrms.kdt.voucher.service.dto.VoucherResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/voucher")
@RestController
public class VoucherApiController {
    private final VoucherService voucherService;
    private final ControllerVoucherMapper mapper;

    public VoucherApiController(VoucherService voucherService, ControllerVoucherMapper mapper) {
        this.voucherService = voucherService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<VoucherResponse> create(@RequestBody ControllerCreateVoucherRequest request) {
        VoucherResponse response = voucherService.createVoucher(mapper.controllerDtoToServiceDto(request));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VoucherDetailResponse> findById(@PathVariable UUID id) {
        VoucherDetailResponse response = voucherService.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<VoucherResponses> findByType(@PathVariable VoucherType type) {
        VoucherResponses response = voucherService.findByType(type);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<VoucherResponses> findAll() {
        VoucherResponses response = voucherService.findAll();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        voucherService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
