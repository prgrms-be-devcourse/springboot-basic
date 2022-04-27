package org.prgrms.springbootbasic.engine.controller;

import org.prgrms.springbootbasic.engine.controller.dto.VoucherCreateRequestDto;
import org.prgrms.springbootbasic.engine.controller.dto.VoucherResponseDto;
import org.prgrms.springbootbasic.engine.domain.Voucher;
import org.prgrms.springbootbasic.engine.service.VoucherService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController()
public class VoucherRestController {
    private final VoucherService voucherService;

    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/api/v1/vouchers")
    public List<VoucherResponseDto> showAllVouchers() {
        return voucherService.getAllVouchers().stream().map(VoucherResponseDto::new).toList();
    }

    @PostMapping("/api/v1/vouchers/new")
    public VoucherResponseDto createVoucher(@RequestBody VoucherCreateRequestDto voucherCreateRequestDto) {
        Voucher voucher = voucherService.insertVoucher(voucherCreateRequestDto.toEntity());
        return new VoucherResponseDto(voucher);
    }

    @GetMapping("/api/v1/vouchers/{voucherId}")
    public VoucherResponseDto showVoucherById(@PathVariable UUID voucherId) {
        Voucher voucher = voucherService.getVoucher(voucherId);
        return new VoucherResponseDto(voucher);
    }

    @GetMapping("/api/v1/vouchers/{voucherId}/delete")
    public String deleteVoucherById(@PathVariable UUID voucherId) {
        Voucher voucher = voucherService.getVoucher(voucherId);
        voucherService.removeVoucher(voucher);
        return "Ok";
    }
}
