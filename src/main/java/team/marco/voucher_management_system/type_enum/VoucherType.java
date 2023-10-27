package team.marco.voucher_management_system.type_enum;

import team.marco.voucher_management_system.model.FixedAmountVoucher;
import team.marco.voucher_management_system.model.LoadedVoucher;
import team.marco.voucher_management_system.model.PercentDiscountVoucher;
import team.marco.voucher_management_system.model.Voucher;

public enum VoucherType {
    FIXED, PERCENT;

    public static Voucher convertVoucher(LoadedVoucher loadedVoucher) {
        return switch (loadedVoucher.getType()) {
            case FIXED -> new FixedAmountVoucher(loadedVoucher.getId(), loadedVoucher.getData());
            case PERCENT -> new PercentDiscountVoucher(loadedVoucher.getId(), loadedVoucher.getData());
        };
    }
}
