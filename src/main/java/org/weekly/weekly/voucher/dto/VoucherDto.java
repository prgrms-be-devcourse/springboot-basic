package org.weekly.weekly.voucher.dto;

import org.weekly.weekly.util.DiscountMap;
import org.weekly.weekly.voucher.domain.Discount;
import org.weekly.weekly.voucher.domain.Voucher;
import org.weekly.weekly.voucher.exception.VoucherException;

import java.time.LocalDate;
import java.util.UUID;

public class VoucherDto {
    private final UUID voucherId;
    private final long amount;

    private final LocalDate registrationDate;
    private final LocalDate expirationDate;

    private final Discount discount;

    private VoucherDto(UUID voucherId, long amount, LocalDate registrationDate, LocalDate expirationDate, Discount discount) {
        this.voucherId = voucherId;
        this.amount = amount;
        this.registrationDate = registrationDate;
        this.expirationDate = expirationDate;
        this.discount = discount;
    }

    public static VoucherDto parseDto(UUID voucherId, String amount, LocalDate registrationDate, String expiration, Discount discount) {
        checkException(amount, registrationDate, expiration, discount);
        return new VoucherDto(voucherId
                , Long.parseLong(amount)
                , registrationDate
                , registrationDate.plusMonths(Long.parseLong(expiration))
                , discount);
    }

    private static void checkException(String amount, LocalDate registrationDate, String expirationMonth, Discount discount) {
        VoucherException.expirationError(registrationDate, expirationMonth);
        if (discount.equals(DiscountMap.PERCENT)) {
            VoucherException.notNumberFormat(amount, input -> Long.parseLong(input) < 0 || Long.parseLong(input) > 100);
            return;
        }

        VoucherException.notNumberFormat(amount, input -> Long.parseLong(input) < 0);
        VoucherException.notNumberFormat(expirationMonth, input -> Long.parseLong(input) < 1);
    }

    public Voucher parseWith(Discount discount) {
        return new Voucher(this.voucherId, this.amount, this.registrationDate, this.expirationDate, discount);
    }

    public UUID getVoucherId() {
        return voucherId;
    }
}