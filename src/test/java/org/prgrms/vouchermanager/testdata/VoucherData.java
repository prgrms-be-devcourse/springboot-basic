package org.prgrms.vouchermanager.testdata;

import org.prgrms.vouchermanager.domain.voucher.FixedAmountVoucher;
import org.prgrms.vouchermanager.domain.voucher.PercentDiscountVoucher;
import org.prgrms.vouchermanager.domain.voucher.Voucher;
import org.prgrms.vouchermanager.domain.voucher.MenuType;

import java.util.UUID;

public class VoucherData {

    public static Voucher getFixedVoucher(){
        return new FixedAmountVoucher(UUID.randomUUID(), 10, MenuType.FIXED);
    }
    public static Voucher getPercentVoucher(){
        return new PercentDiscountVoucher(UUID.randomUUID(), 10, MenuType.FIXED);
    }
}
