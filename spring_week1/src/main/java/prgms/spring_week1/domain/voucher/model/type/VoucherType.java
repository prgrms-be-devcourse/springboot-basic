package prgms.spring_week1.domain.voucher.model.type;

import java.util.Optional;
import java.util.stream.Stream;

public enum VoucherType {
    FIXED("FixedAmountVoucher"),
    PERCENT("PercentDiscountVoucher");

    private final String voucherType;

    public String getVoucherType() {
        return voucherType;
    }

    VoucherType(String voucherType) {
        this.voucherType = voucherType;
    }


}
