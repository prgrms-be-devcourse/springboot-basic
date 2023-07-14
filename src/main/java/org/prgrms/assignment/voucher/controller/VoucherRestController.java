package org.prgrms.assignment.voucher.controller;


import lombok.RequiredArgsConstructor;
import org.prgrms.assignment.voucher.dto.VoucherCreateRequestDTO;
import org.prgrms.assignment.voucher.dto.VoucherResponseDTO;
import org.prgrms.assignment.voucher.dto.VoucherServiceRequestDTO;
import org.prgrms.assignment.voucher.model.Voucher;
import org.prgrms.assignment.voucher.model.VoucherType;
import org.prgrms.assignment.voucher.service.VoucherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestMapping("api/v1/vouchers")
@RestController
@RequiredArgsConstructor
public class VoucherRestController {

    private final VoucherService voucherService;

    @GetMapping("")
    public List<VoucherResponseDTO> getAllVouchers() {
        return voucherService.getAllVoucherDTOs();
    }

    @GetMapping("/{voucherId}")
    public ResponseEntity<VoucherResponseDTO> getVoucherById(@PathVariable("voucherId") UUID voucherId) {
        Optional<VoucherResponseDTO> voucher = voucherService.getVoucherById(voucherId);
        return voucher.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/new")
    public ResponseEntity createVoucher(@RequestBody VoucherCreateRequestDTO voucherRequestDTO) {
        Voucher voucher = voucherService.createVoucher(VoucherServiceRequestDTO.of(voucherRequestDTO));
        return ResponseEntity.ok(voucher);
    }

    @DeleteMapping("/{voucherId}")
    public ResponseEntity deleteVoucherById(@PathVariable("voucherId") UUID voucherId) {
        voucherService.delete(voucherId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/")
    public List<VoucherResponseDTO> getVouchersByType(@RequestParam("voucherType") String voucherType) {
        return voucherService.getVouchersByType(VoucherType.of(voucherType));
    }

}
