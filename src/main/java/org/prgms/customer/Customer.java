package org.prgms.customer;

import org.prgms.validator.Validators;
import org.prgms.voucher.Voucher;

import java.util.List;
import java.util.UUID;

public record Customer(UUID customerId, String name, String email, List<Voucher> vouchers) {
    public Customer {
        Validators.notNullAndEmptyCheck(customerId, name, email, vouchers);
    }

    public void addVoucher(Voucher voucher) {
        vouchers.add(voucher);
    }

    public List<Voucher> getVouchers() {
        return this.vouchers;
    }
}
