package org.weekly.weekly.voucher.domain;

import org.weekly.weekly.util.ExceptionMsg;
import org.weekly.weekly.voucher.exception.VoucherException;
import org.weekly.weekly.voucher.exception.VoucherValidator;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.UUID;

public class Voucher {

    private final UUID voucherId;

    private final LocalDate registrationDate;
    private final LocalDate expirationDate;

    private long amount;

    private Discount discount;

    private Voucher(UUID voucherId, long amount, LocalDate registrationDate, LocalDate expirationDate, Discount discount) {
        this.voucherId = voucherId;
        this.amount = amount;
        this.registrationDate = registrationDate;
        this.expirationDate = expirationDate;
        this.discount = discount;
    }

    public static Voucher of(UUID id, long amount, LocalDate now, long expiration, DiscountType discountType) {
        validation(now, expiration, discountType, amount);

        try {
            return new Voucher(id, amount, now, now.plusMonths(expiration), discountType.getNewInstance());
        } catch (Exception exception) {
            throw new VoucherException(ExceptionMsg.NOT_FOUND);
        }
    }

    private static void validation(LocalDate registrationDate, long expiration, DiscountType discountType, long amount) {
        VoucherValidator.validateExpiration(registrationDate, expiration);
        VoucherValidator.validateAmount(discountType, amount);
    }

    public long applyDiscount(long beforeAmount) {
        long afterAmount = discount.applyDiscount(beforeAmount, amount);

        if (afterAmount >= 0) {
            return afterAmount;
        }

        this.amount -= afterAmount;
        return 0L;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public long getAmount() {
        return amount;
    }

    public DiscountType getDiscountType() {
        return discount.discountType();
    }
}
