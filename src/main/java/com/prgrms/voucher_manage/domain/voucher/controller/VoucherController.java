package com.prgrms.voucher_manage.domain.voucher.controller;

import com.prgrms.voucher_manage.domain.voucher.dto.CreateVoucherDto;
import com.prgrms.voucher_manage.domain.voucher.dto.UpdateVoucherDto;
import com.prgrms.voucher_manage.domain.voucher.entity.Voucher;
import com.prgrms.voucher_manage.domain.voucher.entity.VoucherType;
import com.prgrms.voucher_manage.domain.voucher.service.VoucherService;
import com.prgrms.voucher_manage.exception.InvalidInputException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

import static com.prgrms.voucher_manage.domain.voucher.entity.FixedAmountVoucher.isInvalidPrice;
import static com.prgrms.voucher_manage.domain.voucher.entity.PercentDiscountVoucher.isInvalidPercent;
import static com.prgrms.voucher_manage.domain.voucher.entity.VoucherType.FIXED;
import static com.prgrms.voucher_manage.domain.voucher.entity.VoucherType.PERCENT;

@Controller
@RequiredArgsConstructor
public class VoucherController {
    private final VoucherService voucherService;

    public void saveVoucher(VoucherType voucherType, Long discountAmount) {
        validateVoucher(voucherType, discountAmount);
        voucherService.createVoucher(new CreateVoucherDto(voucherType, discountAmount));
    }
    public List<Voucher> getVouchers() {
        return voucherService.getVouchers();
    }

    public Voucher findVoucher(UUID voucherId){
        return voucherService.findVoucher(voucherId);
    }
    public void updateVoucher(UUID voucherId, Long discountAmount){
        VoucherType type = voucherService.findVoucher(voucherId).getType();
        validateVoucher(type, discountAmount);
        voucherService.updateVoucher(new UpdateVoucherDto(voucherId,type, discountAmount));
    }

    public void deleteVoucher(UUID voucherId){
        voucherService.deleteVoucher(voucherId);
    }

    private static void validateVoucher(VoucherType voucherType, Long discountAmount) {
        if (voucherType == FIXED && isInvalidPrice(discountAmount)) {
            throw new InvalidInputException("Invalid discount range.");
        } else if (voucherType == PERCENT && isInvalidPercent(discountAmount)) {
            throw new InvalidInputException("Invalid discount range.");
        }
    }
}
