package com.programmers.vouchermanagement.voucher.presentation;

import com.programmers.vouchermanagement.voucher.application.VoucherService;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;
import com.programmers.vouchermanagement.voucher.dto.VoucherRequestDto;
import com.programmers.vouchermanagement.voucher.dto.VoucherResponseDto;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/voucher")
public class VoucherApiController {

    private final VoucherService voucherService;

    public VoucherApiController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @PostMapping
    public void createVoucher(@RequestBody VoucherRequestDto voucherRequestDto) {
        voucherService.createVoucher(voucherRequestDto);
    }

    @GetMapping("list")
    public List<VoucherResponseDto> readAllVoucher() {
        return voucherService.readAllVoucher();
    }

    @GetMapping("/{voucherId}")
    public VoucherResponseDto readVoucherById(@PathVariable UUID voucherId) {
        return voucherService.readVoucherById(voucherId);
    }

    @GetMapping
    public List<VoucherResponseDto> readAllVoucherByCreatedAtAndType(@RequestParam LocalDateTime createdAt, @RequestParam VoucherType voucherType) {
        return voucherService.readAllVoucherByCreatedAtAndType(createdAt, voucherType);
    }

    @DeleteMapping
    public void removeAllVoucher() {
        voucherService.removeAllVoucher();
    }

    @DeleteMapping("/{voucherId}")
    public void removeVoucherById(@PathVariable UUID voucherId) {
        voucherService.removeVoucherById(voucherId);
    }
}
