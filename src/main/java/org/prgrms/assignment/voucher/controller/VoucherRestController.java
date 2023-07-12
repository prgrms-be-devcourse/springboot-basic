package org.prgrms.assignment.voucher.controller;


import lombok.RequiredArgsConstructor;
import org.prgrms.assignment.voucher.dto.VoucherRequestDTO;
import org.prgrms.assignment.voucher.dto.VoucherResponseDTO;
import org.prgrms.assignment.voucher.model.Voucher;
import org.prgrms.assignment.voucher.model.VoucherType;
import org.prgrms.assignment.voucher.service.VoucherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class VoucherRestController {

    private final VoucherService voucherService;

    @GetMapping("api/v1/vouchers")
    public List<VoucherResponseDTO> getAllVouchers() {
        return voucherService.getAllVoucherDTOs();
    }

    @GetMapping("api/v1/vouchers/{voucherId}")
    public ResponseEntity<VoucherResponseDTO> getVoucherById(@PathVariable("voucherId") UUID voucherId) {
        Optional<VoucherResponseDTO> voucher = voucherService.getVoucherById(voucherId);
        return voucher.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("api/v1/vouchers/new")
    public ResponseEntity createVoucher(@RequestBody VoucherRequestDTO voucherRequestDTO) {
        Voucher voucher = voucherService.createVoucher(voucherRequestDTO.voucherType(),
            voucherRequestDTO.benefit(),
            voucherRequestDTO.durationDate());
        return ResponseEntity.ok(voucher);
    }

    @DeleteMapping("api/v1/vouchers/{voucherId}")
    public ResponseEntity deleteVoucherById(@PathVariable("voucherId") UUID voucherId) {
        voucherService.delete(voucherId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("api/v1/vouchers/type/{voucherType}")
    public List<VoucherResponseDTO> getVouchersByType(@PathVariable String voucherType) {
        return voucherService.getVouchersByType(VoucherType.of(voucherType));
    }

}
