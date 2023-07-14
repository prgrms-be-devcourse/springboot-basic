package org.weekly.weekly.voucher.dto.response;

import org.weekly.weekly.voucher.domain.Voucher;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.UUID;

public class VoucherCreationResponse {
    private final UUID id;
    private final LocalDate registrationDate;
    private final LocalDate expirationDate;
    private final long amount;
    private final String voucherType;

    public VoucherCreationResponse(Voucher voucher) {
        this.id = voucher.getVoucherId();
        this.registrationDate = voucher.getRegistrationDate();
        this.expirationDate = voucher.getExpirationDate();
        this.amount = voucher.getAmount();
        this.voucherType = voucher.getDiscountType().name();
    }

    public UUID getId() {
        return id;
    }

    public long getAmount() {
        return amount;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public String result() {
        return MessageFormat.format("[ID: {0}, 금액: {1}, 등록일자: {2}, 유효기간: {3}]", id, amount, registrationDate, expirationDate);
    }
}
