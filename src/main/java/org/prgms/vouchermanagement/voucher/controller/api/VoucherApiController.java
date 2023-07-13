package org.prgms.vouchermanagement.voucher.controller.api;

import lombok.RequiredArgsConstructor;
import org.prgms.vouchermanagement.voucher.domain.dto.VoucherDto;
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
    public VoucherDto createVoucher(@RequestBody VoucherDto voucherDto) {
        return voucherService.createNewVoucher(voucherDto);
    }

    @GetMapping("/detail/{id}")
    public VoucherDto voucherDetail(@PathVariable("id") UUID voucherId) {
        Optional<VoucherDto> voucher = voucherService.findVoucherById(voucherId);
        return voucher.orElseGet(VoucherDto::new);
    }

    @PostMapping("/update")
    public VoucherDto updateVoucher(@RequestBody VoucherDto voucherDto) {
        return voucherService.updateVoucher(voucherDto);
    }
}
