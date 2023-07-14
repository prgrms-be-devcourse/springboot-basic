package org.weekly.weekly.voucher.dto.response;

import org.weekly.weekly.voucher.domain.Voucher;
import org.weekly.weekly.voucher.dto.Response;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.UUID;

public class VoucherCreationResponse implements Response {
    private final UUID id;
    private final LocalDate registrationDate;
    private final LocalDate expirationDate;
    private final long amount;

    public VoucherCreationResponse(Voucher voucher) {
        this.id = voucher.getVoucherId();
        this.registrationDate = voucher.getRegistrationDate();
        this.expirationDate = voucher.getExpirationDate();
        this.amount = voucher.getAmount();
    }

    @Override
    public String getResult() {
        return MessageFormat.format("[ID: {0}, 금액: {1}, 등록일자: {2}, 유효기간: {3}]", id, amount, registrationDate, expirationDate);
    }
}
