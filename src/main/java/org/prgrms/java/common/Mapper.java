package org.prgrms.java.common;

import org.prgrms.java.domain.user.User;
import org.prgrms.java.domain.voucher.FixedAmountVoucher;
import org.prgrms.java.domain.voucher.PercentDiscountVoucher;
import org.prgrms.java.domain.voucher.Voucher;

import java.util.UUID;

public class Mapper {
    private static final String PERCENT_PATTERN = "[0-9]+%";

    private Mapper() {
    }

    public static Voucher mapToVoucher(String object) {
        String[] atoms = object.split(",");
        UUID voucherId = UUID.fromString(atoms[0].trim());
        String amount = atoms[1].trim();
        if (amount.matches(PERCENT_PATTERN)) {
            return new PercentDiscountVoucher(voucherId, Long.parseLong(amount.substring(0, amount.length()-1)));
        } else {
            return new FixedAmountVoucher(voucherId, Long.parseLong(amount));
        }
    }

    public static User mapToUser(String object, boolean isBlocked) {
        String[] atoms = object.split(",");
        UUID userId = UUID.fromString(atoms[0].trim());
        String name = atoms[1].trim();
        String email = atoms[2].trim();

        return new User(userId, name, email, isBlocked);
    }
}
