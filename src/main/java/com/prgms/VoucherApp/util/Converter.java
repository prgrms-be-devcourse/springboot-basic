package com.prgms.VoucherApp.util;

import com.prgms.VoucherApp.domain.customer.model.Customer;
import com.prgms.VoucherApp.domain.customer.model.CustomerStatus;
import com.prgms.VoucherApp.domain.voucher.model.FixedAmountVoucher;
import com.prgms.VoucherApp.domain.voucher.model.PercentDiscountVoucher;
import com.prgms.VoucherApp.domain.voucher.model.Voucher;
import com.prgms.VoucherApp.domain.voucher.model.VoucherType;

import java.math.BigDecimal;
import java.util.UUID;

public final class Converter {
    private static final String DELIMITER = ",";

    private Converter() {
    }

    public static Voucher convertToVoucher(String record) {
        String[] voucherField = record.split(DELIMITER);
        UUID voucherId = UUID.fromString(voucherField[0]);
        BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(voucherField[1]));
        VoucherType voucherType = VoucherType.findByVoucherTypeName(voucherField[2]);

        if (voucherType.isFixedVoucher()) {
            return new FixedAmountVoucher(voucherId, amount);
        }

        return new PercentDiscountVoucher(voucherId, amount);
    }

    public static Customer convertToBlacklist(String record) {
        UUID customerId = UUID.fromString(record);

        return new Customer(customerId, CustomerStatus.BLACKLIST);
    }

    public static String toString(Voucher voucher) {
        return String.format("%s,%s,%s",
            voucher.getVoucherId(), voucher.getAmount(), voucher.getVoucherType().getVoucherTypeName());
    }
}
