package org.prgms.vouchermanagement.voucher.controller.api;

import lombok.RequiredArgsConstructor;
import org.prgms.vouchermanagement.voucher.domain.dto.response.VoucherResponseDto;
import org.prgms.vouchermanagement.voucher.domain.dto.request.VoucherSaveRequestDto;
import org.prgms.vouchermanagement.voucher.domain.entity.Voucher;
import org.prgms.vouchermanagement.voucher.service.ThymeleafVoucherService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/vouchers")
@RequiredArgsConstructor
public class VoucherApiController {
    private final ThymeleafVoucherService voucherService;

    @GetMapping("/list")
    public List<Voucher> voucherList() {
        return voucherService.getVouchers();
    }

    @PostMapping("/create")
    public VoucherSaveRequestDto createVoucher(@RequestBody VoucherSaveRequestDto voucherDto) {
        return voucherService.createNewVoucher(voucherDto);
    }

    @GetMapping("/detail/{id}")
    public VoucherResponseDto voucherDetail(@PathVariable("id") UUID voucherId) {
        Optional<VoucherResponseDto> voucher = voucherService.findVoucherById(voucherId);
        return voucher.orElseThrow();
    }

    @PostMapping("/update")
    public VoucherResponseDto updateVoucher(@RequestBody VoucherResponseDto voucherDto) {
        return voucherService.updateVoucher(voucherDto);
    }
}
