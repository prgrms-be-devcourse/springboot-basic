package org.weekly.weekly.voucher.dto;

import org.weekly.weekly.util.DiscountType;
import org.weekly.weekly.voucher.domain.Discount;
import org.weekly.weekly.voucher.domain.Voucher;
import org.weekly.weekly.voucher.exception.VoucherException;

import java.time.LocalDate;
import java.util.UUID;

public class VoucherDto {
    private static final int RANGE_START = 0;
    private static final int RANGE_END = 100;

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

    public static VoucherDto parseDto(UUID voucherId, VoucherInfoRequest voucherInfoRequest, Discount discount, LocalDate registrationDate) {
        checkException(voucherInfoRequest, registrationDate, discount);
        return new VoucherDto(voucherId
                , Long.parseLong(voucherInfoRequest.getAmount())
                , registrationDate
                , registrationDate.plusMonths(Long.parseLong(voucherInfoRequest.getExpiration()))
                , discount);
    }

    private static void checkException(VoucherInfoRequest voucherInfoRequest, LocalDate registrationDate, Discount discount) {
        VoucherException.expirationError(registrationDate, voucherInfoRequest.getExpiration());
        VoucherException.notNumberFormat(voucherInfoRequest.getExpiration(), input -> Long.parseLong(input) <= RANGE_START);

        if (discount.getClass().equals(DiscountType.PERCENT.getCls())) {
            VoucherException.notNumberFormat(voucherInfoRequest.getAmount(), input -> Long.parseLong(input) < RANGE_START || Long.parseLong(input) > RANGE_END);
            return;
        }
        VoucherException.notNumberFormat(voucherInfoRequest.getAmount(), input -> Long.parseLong(input) < RANGE_START);

    }

    public Voucher parseToVoucher() {
        return new Voucher(this.voucherId, this.amount, this.registrationDate, this.expirationDate, this.discount);
    }

    public UUID getVoucherId() {
        return voucherId;
    }
}