package com.programmers.vouchermanagement.voucher.presentation;

import com.programmers.vouchermanagement.voucher.application.VoucherService;
import com.programmers.vouchermanagement.voucher.dto.VoucherRequestDto;
import com.programmers.vouchermanagement.voucher.dto.VoucherResponseDto;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class VoucherController {
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public void createVoucher(VoucherRequestDto voucherRequestDto) {
        voucherService.createVoucher(voucherRequestDto);
    }

    public List<VoucherResponseDto> readAllVoucher() {
        return voucherService.readAllVoucher();
    }

    public VoucherResponseDto readVoucherById(UUID voucherId) {
        return voucherService.readVoucherById(voucherId);
    }

    public void updateVoucher(UUID voucherId, VoucherRequestDto voucherRequestDto) {
        voucherService.updateVoucher(voucherId, voucherRequestDto);
    }

    public void removeAllVoucher() {
        voucherService.removeAllVoucher();
    }

    public void removeVoucherById(UUID voucherId) {
        voucherService.removeVoucherById(voucherId);
    }
}
