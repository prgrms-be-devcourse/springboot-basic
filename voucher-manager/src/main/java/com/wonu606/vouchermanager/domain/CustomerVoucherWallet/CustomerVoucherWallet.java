package com.wonu606.vouchermanager.domain.CustomerVoucherWallet;

import com.wonu606.vouchermanager.domain.customer.emailAddress.EmailAddress;
import java.util.Objects;
import java.util.UUID;

public class CustomerVoucherWallet {

    private final UUID voucherId;
    private final EmailAddress emailAddress;

    public CustomerVoucherWallet(UUID voucherId, EmailAddress emailAddress) {
        this.voucherId = voucherId;
        this.emailAddress = emailAddress;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public String getEmailAddress() {
        return emailAddress.getAddress();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CustomerVoucherWallet that = (CustomerVoucherWallet) o;
        return Objects.equals(getVoucherId(), that.getVoucherId())
                && Objects.equals(getEmailAddress(), that.getEmailAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getVoucherId(), getEmailAddress());
    }
}
