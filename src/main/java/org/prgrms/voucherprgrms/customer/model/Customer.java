package org.prgrms.voucherprgrms.customer.model;

import org.prgrms.voucherprgrms.voucher.model.Voucher;

import java.util.Optional;
import java.util.UUID;

public class Customer {

    private UUID customerId;
    private String name;
    private String email;

    //TODO UUID/Voucher 형태로 둘 것인지. Optional을 씌울 것인지.
    private Optional<Voucher> voucher = Optional.empty();

    public Customer(UUID customerId, String name, String email) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
    }

    public void allocateVoucher(Voucher voucher) {
        this.voucher = Optional.of(voucher);
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Optional<Voucher> getVoucher() {
        return voucher;
    }
}
