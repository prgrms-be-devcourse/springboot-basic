package org.prgrms.springbootbasic.engine.controller;

import org.prgrms.springbootbasic.engine.controller.dto.VoucherCreateRequestDto;
import org.prgrms.springbootbasic.engine.controller.dto.VoucherResponseDto;
import org.prgrms.springbootbasic.engine.domain.Voucher;
import org.prgrms.springbootbasic.engine.service.VoucherService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.prgrms.springbootbasic.engine.util.UUIDUtil.convertStringToUUID;

@RestController
public class VoucherRestController {
    private final VoucherService voucherService;

    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/api/v1/vouchers")
    public List<VoucherResponseDto> showAllVouchers(@RequestParam Optional<String> voucherType, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Optional<LocalDateTime> afterCreatedAt, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Optional<LocalDateTime> beforeCreatedAt) {
        return voucherService.getVouchersByConditions(voucherType, afterCreatedAt, beforeCreatedAt).stream().map(VoucherResponseDto::new).toList();
    }

    @PostMapping("/api/v1/vouchers/new")
    public VoucherResponseDto createVoucher(@RequestBody VoucherCreateRequestDto voucherCreateRequestDto) {
        Voucher voucher = voucherService.insertVoucher(voucherCreateRequestDto.toEntity());
        return new VoucherResponseDto(voucher);
    }

    @GetMapping("/api/v1/vouchers/{voucherId}")
    public VoucherResponseDto showVoucherById(@PathVariable String voucherId) {
        UUID id = convertStringToUUID(voucherId);
        Voucher voucher = voucherService.getVoucher(id);
        return new VoucherResponseDto(voucher);
    }

    @DeleteMapping("/api/v1/vouchers/{voucherId}/delete")
    public String deleteVoucherById(@PathVariable String voucherId) {
        UUID id = convertStringToUUID(voucherId);
        voucherService.removeVoucherById(id);
        return "Ok";
    }
}
