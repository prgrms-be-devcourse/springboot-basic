package org.prgrms.voucherprgrms.customer.model;

import org.prgrms.voucherprgrms.voucher.model.Voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {

    private final UUID customerId;
    private String name;
    private final String email;
    private final LocalDateTime createdAt;

    private UUID voucherId = null;

    public Customer(UUID customerId, String name, String email, LocalDateTime createdAt) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }

    public Customer(UUID customerId, String name, String email, LocalDateTime createdAt, UUID voucherId) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.voucherId = voucherId;
    }

    public boolean allocateVoucher(Voucher voucher) {
        if (this.voucherId != null) {
            return false;
        } else {
            this.voucherId = voucher.getVoucherId();
            return true;
        }
    }

    public void removeVoucher() {
        this.voucherId = null;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
