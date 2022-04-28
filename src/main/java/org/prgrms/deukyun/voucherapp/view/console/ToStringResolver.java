package org.prgrms.deukyun.voucherapp.view.console;

import org.prgrms.deukyun.voucherapp.domain.voucher.domain.FixedAmountDiscountVoucher;
import org.prgrms.deukyun.voucherapp.domain.voucher.domain.PercentDiscountVoucher;
import org.prgrms.deukyun.voucherapp.domain.voucher.domain.Voucher;

public class ToStringResolver {

    public static String voucherToString (Voucher voucher){
        String id = voucher.getId().toString().substring(0, 8);

        if(voucher instanceof FixedAmountDiscountVoucher){
            long amount = ((FixedAmountDiscountVoucher) voucher).getAmount();

            return String.format("[Fixed Amount Discount Voucher] id : %s, amount  : %d₩", id, amount);

        }else if(voucher instanceof PercentDiscountVoucher){
            long percent = ((PercentDiscountVoucher) voucher).getPercent();

            return String.format("[Percent Discount Voucher]      id : %s, percent : %d%%", id, percent);

        }else{
            throw new IllegalArgumentException("Invalid voucher type given");
        }
    }
}
