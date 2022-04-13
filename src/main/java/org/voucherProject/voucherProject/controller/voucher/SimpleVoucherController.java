package org.voucherProject.voucherProject.controller.voucher;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.voucherProject.voucherProject.entity.voucher.Voucher;
import org.voucherProject.voucherProject.entity.voucher.VoucherDto;
import org.voucherProject.voucherProject.entity.voucher.VoucherType;
import org.voucherProject.voucherProject.service.voucher.VoucherService;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class SimpleVoucherController implements VoucherController {

    private final VoucherService voucherService;

    @Override
    public Voucher createVoucher(VoucherDto voucherDto){
        VoucherType voucherType = voucherDto.getVoucherType();
        long amount = voucherDto.getAmount();
        return voucherService.save(voucherType.createVoucher(amount));
    }

    @Override
    public List<VoucherDto> findAll() {
        List<Voucher> vouchers = voucherService.findAll();
        return vouchers.stream()
                .map(v -> new VoucherDto(v.getVoucherType(), v.getHowMuch(), v.getVoucherStatus()))
                .toList();
    }

    @Override
    public VoucherDto findById(UUID voucherId) {
        Voucher voucher = voucherService.getVoucher(voucherId);
        return VoucherDto.builder()
                .voucherType(voucher.getVoucherType())
                .voucherStatus(voucher.getVoucherStatus())
                .amount(voucher.getHowMuch())
                .build();
    }
}
