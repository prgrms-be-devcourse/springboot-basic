package com.wonu606.vouchermanager.domain.voucherwallet;

import com.wonu606.vouchermanager.domain.customer.email.Email;
import java.util.Objects;
import java.util.UUID;

public class VoucherWallet {

    private final UUID voucherUuid;
    private final Email email;

    public VoucherWallet(UUID voucherUuid, Email email) {
        this.voucherUuid = voucherUuid;
        this.email = email;
    }

    public UUID getVoucherUuid() {
        return voucherUuid;
    }

    public String getEmailAddress() {
        return email.getAddress();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VoucherWallet that = (VoucherWallet) o;
        return Objects.equals(getVoucherUuid(), that.getVoucherUuid())
                && Objects.equals(getEmailAddress(), that.getEmailAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getVoucherUuid(), getEmailAddress());
    }
}
