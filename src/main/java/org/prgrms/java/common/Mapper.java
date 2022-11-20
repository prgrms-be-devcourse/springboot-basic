package org.prgrms.java.common;

import org.prgrms.java.domain.customer.Customer;
import org.prgrms.java.domain.voucher.FixedAmountVoucher;
import org.prgrms.java.domain.voucher.PercentDiscountVoucher;
import org.prgrms.java.domain.voucher.Voucher;
import org.prgrms.java.exception.VoucherException;

import java.time.LocalDateTime;
import java.util.UUID;

public class Mapper {
    private Mapper() {
    }

    public static Voucher mapToVoucher(String object) {
        String[] atoms = object.split(",");
        UUID voucherId = UUID.fromString(atoms[0].trim());
        String amount = atoms[1].trim();
        String type = atoms[2].trim();
        LocalDateTime expiredAt = LocalDateTime.parse(atoms[3].trim());
        boolean used = Boolean.parseBoolean(atoms[3].trim());

        return mapToVoucher(type, voucherId, Long.parseLong(amount), expiredAt, used);
    }

    public static Voucher mapToVoucher(String type, UUID voucherId, long amount, LocalDateTime expiredAt, boolean used) {
        switch (type) {
            case "PERCENT":
                return new PercentDiscountVoucher(voucherId, amount, used, expiredAt);
            case "FIXED":
                return new FixedAmountVoucher(voucherId, amount, used, expiredAt);
            default:
                throw new VoucherException("Unknown voucher type.");
        }
    }

    public static Customer mapToCustomer(String object) {
        String[] atoms = object.split(",");
        UUID customerId = UUID.fromString(atoms[0].trim());
        String name = atoms[1].trim();
        String email = atoms[2].trim();
        boolean isBlocked = Boolean.parseBoolean(atoms[3].trim());

        return new Customer(customerId, name, email, isBlocked);
    }
}
