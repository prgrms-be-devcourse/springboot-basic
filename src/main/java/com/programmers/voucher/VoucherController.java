package com.programmers.voucher;

import com.programmers.TypeOfVoucher;
import org.springframework.stereotype.Controller;

import java.util.Map;
import java.util.UUID;

@Controller
public class VoucherController {
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public void createByType(TypeOfVoucher typeOfVoucher, long discount) {
        switch (typeOfVoucher) {
            case FIXED_AMOUNT_VOUCHER -> voucherService.createFixedAmountVoucher(discount);
            case PERCENT_DISCOUNT_VOUCHER -> voucherService.createPercentDiscountVoucher(discount);
        }
    }

    public Map<UUID, Voucher> findAll() {
        return voucherService.findAll();
    }
}
