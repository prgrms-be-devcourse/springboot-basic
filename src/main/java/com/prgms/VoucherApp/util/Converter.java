package com.prgms.VoucherApp.util;

import com.prgms.VoucherApp.domain.customer.Customer;
import com.prgms.VoucherApp.domain.customer.CustomerStatus;
import com.prgms.VoucherApp.domain.voucher.FixedAmountVoucher;
import com.prgms.VoucherApp.domain.voucher.PercentDiscountVoucher;
import com.prgms.VoucherApp.domain.voucher.Voucher;
import com.prgms.VoucherApp.domain.voucher.VoucherType;

import java.math.BigDecimal;
import java.util.UUID;

public class Converter {
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
