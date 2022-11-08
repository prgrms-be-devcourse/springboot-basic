package org.prgrms.java.common;

import org.prgrms.java.domain.voucher.FixedAmountVoucher;
import org.prgrms.java.domain.voucher.PercentDiscountVoucher;
import org.prgrms.java.domain.voucher.Voucher;

import java.util.UUID;

public class Mapper {
    public static Voucher mapToVoucher(String object) {
        String[] atoms = object.split(",");
        UUID voucherId = UUID.fromString(atoms[0].trim());
        String amount = atoms[1].trim();
        if (amount.matches("[0-9]+%")) {
            return new PercentDiscountVoucher(voucherId, Long.parseLong(amount.substring(0, amount.length()-1)));
        } else {
            return new FixedAmountVoucher(voucherId, Long.parseLong(amount));
        }
    }
}
