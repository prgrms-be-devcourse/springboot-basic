package org.voucherProject.voucherProject.controller.voucher;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.voucherProject.voucherProject.entity.voucher.FixedAmountVoucher;
import org.voucherProject.voucherProject.entity.voucher.PercentDiscountVoucher;
import org.voucherProject.voucherProject.entity.voucher.Voucher;
import org.voucherProject.voucherProject.entity.voucher.VoucherType;
import org.voucherProject.voucherProject.service.voucher.VoucherService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class VoucherControllerImpl implements VoucherController {

    private final VoucherService voucherService;

    @Override
    public Voucher createVoucher(VoucherType voucherType, long number){
        UUID voucherId = UUID.randomUUID();
        Optional<Voucher> voucher = Optional.empty();
        if (voucherType.equals(VoucherType.PERCENT)) voucher = Optional.of(new PercentDiscountVoucher(voucherId, number));
        if (voucherType.equals(VoucherType.FIXED)) voucher = Optional.of(new FixedAmountVoucher(voucherId, number));
        return voucherService.save(voucher.get());
    }

    @Override
    public List<Voucher> findAll(){
        return voucherService.findAll();
    }

    @Override
    public Voucher findById(UUID voucherId){
        return voucherService.getVoucher(voucherId);
    }
}
