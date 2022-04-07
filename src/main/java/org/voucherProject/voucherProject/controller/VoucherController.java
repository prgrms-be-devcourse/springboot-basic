package org.voucherProject.voucherProject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.voucherProject.voucherProject.entity.voucher.FixedAmountVoucher;
import org.voucherProject.voucherProject.entity.voucher.PercentDiscountVoucher;
import org.voucherProject.voucherProject.entity.voucher.Voucher;
import org.voucherProject.voucherProject.entity.voucher.VoucherType;
import org.voucherProject.voucherProject.service.VoucherService;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class VoucherController {

    private final VoucherService voucherService;

    public void createVoucher(VoucherType voucherType, long number) {
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = null;
        if (voucherType.equals(VoucherType.PERCENT)) voucher = new PercentDiscountVoucher(voucherId, number, voucherType);
        if (voucherType.equals(VoucherType.FIXED)) voucher = new FixedAmountVoucher(voucherId, number, voucherType);
        voucherService.save(voucher);
    }

    public List<Voucher> findAll() {
        return voucherService.findAll();
    }

}
