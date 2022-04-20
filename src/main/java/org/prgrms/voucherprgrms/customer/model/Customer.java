package org.prgrms.voucherprgrms.customer.model;

import org.prgrms.voucherprgrms.voucher.model.Voucher;

import java.util.Optional;
import java.util.UUID;

public class Customer {

    private UUID customerId;
    private String name;
    private String email;

    //TODO UUID/Voucher 형태로 둘 것인지. Optional을 씌울 것인지.
//    private Optional<Voucher> voucher = Optional.empty();
    private UUID voucherId = null;

    public Customer(UUID customerId, String name, String email) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
    }

    public Customer(UUID customerId, String name, String email, UUID voucherId) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.voucherId = voucherId;
    }

    public boolean allocateVoucher(Voucher voucher) {
        if (this.voucherId == null) {
            return false;
        } else {
            this.voucherId = voucher.getVoucherId();
            return true;
        }
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

    public UUID getVoucherId() {
        return voucherId;
    }
}
