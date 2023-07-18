package com.wonu606.vouchermanager.domain.voucherwallet;

import com.wonu606.vouchermanager.domain.customer.email.Email;
import java.util.UUID;

public class VoucherWalletDto {

    private final UUID voucherId;
    private final String emailAddress;

    public VoucherWalletDto(UUID voucherId, String emailAddress) {
        this.voucherId = voucherId;
        this.emailAddress = emailAddress;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public VoucherWallet toEntity() {
        return new VoucherWallet(getVoucherId(), new Email(getEmailAddress()));
    }
}
