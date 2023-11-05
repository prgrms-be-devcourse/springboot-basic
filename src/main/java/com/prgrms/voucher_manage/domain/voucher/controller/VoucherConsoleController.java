package com.prgrms.voucher_manage.domain.voucher.controller;

import com.prgrms.voucher_manage.domain.voucher.controller.dto.CreateVoucherReq;
import com.prgrms.voucher_manage.domain.voucher.controller.dto.UpdateVoucherReq;
import com.prgrms.voucher_manage.domain.voucher.entity.Voucher;
import com.prgrms.voucher_manage.domain.voucher.entity.VoucherType;
import com.prgrms.voucher_manage.domain.voucher.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

import static com.prgrms.voucher_manage.domain.voucher.entity.FixedAmountVoucher.isInvalidPrice;
import static com.prgrms.voucher_manage.domain.voucher.entity.PercentDiscountVoucher.isInvalidPercent;
import static com.prgrms.voucher_manage.domain.voucher.entity.VoucherType.FIXED;
import static com.prgrms.voucher_manage.domain.voucher.entity.VoucherType.PERCENT;
import static com.prgrms.voucher_manage.base.ErrorMessage.INVALID_DISCOUNT_RANGE;

@Controller
@RequiredArgsConstructor
public class VoucherConsoleController {
    private final VoucherService voucherService;

    public void saveVoucher(VoucherType voucherType, Long discountAmount) {
        validateVoucher(voucherType, discountAmount);
        voucherService.createVoucher(new CreateVoucherReq(voucherType, discountAmount));
    }

    public List<Voucher> getVouchers() {
        return voucherService.getVouchers();
    }

    public List<Voucher> getVoucherByType(VoucherType type){
        return voucherService.getVouchersByType(type);
    }

    public Voucher findVoucher(UUID voucherId) {
        return voucherService.getVoucherById(voucherId);
    }

    public void updateVoucher(UUID voucherId, Long discountAmount) {
        VoucherType type = voucherService.getVoucherById(voucherId).getType();
        validateVoucher(type, discountAmount);
        voucherService.updateVoucher(voucherId, new UpdateVoucherReq(discountAmount));
    }

    public void deleteVoucher(UUID voucherId) {
        voucherService.deleteVoucher(voucherId);
    }

    private static void validateVoucher(VoucherType voucherType, Long discountAmount) {
        if (voucherType == FIXED && isInvalidPrice(discountAmount)) {
            throw new IllegalArgumentException(INVALID_DISCOUNT_RANGE.getMessage());
        } else if (voucherType == PERCENT && isInvalidPercent(discountAmount)) {
            throw new IllegalArgumentException(INVALID_DISCOUNT_RANGE.getMessage());
        }
    }
}
