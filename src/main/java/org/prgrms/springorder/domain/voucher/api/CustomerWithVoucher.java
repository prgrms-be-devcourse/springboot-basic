package org.prgrms.springorder.domain.voucher.api;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import org.prgrms.springorder.domain.customer.model.CustomerStatus;
import org.prgrms.springorder.domain.voucher.model.VoucherType;

public class CustomerWithVoucher {

    private final UUID voucherId;

    private final long amount;

    private final LocalDateTime voucherCreatedAt;

    private final VoucherType voucherType;

    private final UUID customerId;

    private final String name;

    private final String email;

    private final CustomerStatus customerStatus;

    public CustomerWithVoucher(UUID voucherId, long amount,
        LocalDateTime voucherCreatedAt,
        VoucherType voucherType, UUID customerId, String name, String email,
        CustomerStatus customerStatus) {
        this.voucherId = voucherId;
        this.amount = amount;
        this.voucherCreatedAt = voucherCreatedAt;
        this.voucherType = voucherType;
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.customerStatus = customerStatus;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public long getAmount() {
        return amount;
    }

    public LocalDateTime getVoucherCreatedAt() {
        return voucherCreatedAt;
    }

    public VoucherType getVoucherType() {
        return voucherType;
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

    public CustomerStatus getCustomerStatus() {
        return customerStatus;
    }

    @Override
    public String toString() {
        return "voucherId=" + voucherId +
            ", amount=" + amount +
            ", voucherCreatedAt=" + voucherCreatedAt +
            ", voucherType=" + voucherType +
            ", customerId=" + customerId +
            ", name='" + name + '\'' +
            ", email='" + email + '\'' +
            ", customerStatus=" + customerStatus +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CustomerWithVoucher that = (CustomerWithVoucher) o;
        return amount == that.amount && Objects.equals(voucherId, that.voucherId)
            && Objects.equals(voucherCreatedAt, that.voucherCreatedAt)
            && voucherType == that.voucherType && Objects.equals(customerId, that.customerId)
            && Objects.equals(name, that.name) && Objects.equals(email, that.email)
            && customerStatus == that.customerStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(voucherId, amount, voucherCreatedAt, voucherType, customerId, name,
            email,
            customerStatus);
    }
}
