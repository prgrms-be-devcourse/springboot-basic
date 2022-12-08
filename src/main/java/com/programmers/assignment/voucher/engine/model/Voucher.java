package com.programmers.assignment.voucher.engine.model;

import com.programmers.assignment.voucher.util.domain.VoucherVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.UUID;

public class Voucher {

    private final UUID voucherId;
    private String discountWay;
    private long discountValue;
    private long customerId;
    private static final Logger logger = LoggerFactory.getLogger(Voucher.class);

    private static final long MAX_VOUCHER_AMOUNT = 10000;

    private static final long MAX_VOUCHER_PERCENT = 100;

    private static final long MIN_VOUCHER_AMOUNT = 0;

    public Voucher(UUID voucherId, String discountWay, long discountValue, long customerId) throws NoSuchFieldException {
        switch (VoucherVariable.getVoucher(discountWay)) {
            case FIXED:
                validateAmountValue(discountValue);
                break;
            case PERCENT:
                validatePercentValue(discountValue);
                break;
        }
        this.voucherId = voucherId;
        this.discountWay = discountWay;
        this.discountValue = discountValue;
        this.customerId = customerId;
    }

    private static void validateAmountValue(long discountValue) {
        if (discountValue <= MIN_VOUCHER_AMOUNT) {
            logger.error("illegal fixed amount input : " + discountValue);
            throw new IllegalArgumentException("Amount should be over zero");
        }
        if (discountValue > MAX_VOUCHER_AMOUNT) {
            logger.error("illegal fixed amount input : " + discountValue);
            throw new IllegalArgumentException(String.format("Amount should be less than %d", MAX_VOUCHER_AMOUNT));
        }
    }

    private static void validatePercentValue(long discountValue) {
        if (discountValue <= MIN_VOUCHER_AMOUNT) {
            logger.error("illegal percent discount input : " + discountValue);
            throw new IllegalArgumentException("Percent should be over zero");
        }
        if (discountValue > MAX_VOUCHER_PERCENT) {
            logger.error("illegal percent discount input : " + discountValue);
            throw new IllegalArgumentException(String.format("Percent should be less than %d", MAX_VOUCHER_PERCENT));
        }
    }


    public UUID getVoucherId() {
        return voucherId;
    }

    public String getDiscountWay() {
        return discountWay;
    }

    public long getDiscountValue() {
        return discountValue;
    }

    public long getCustomerId() {
        return customerId;
    }

    public long discount(long beforeDiscount) throws NoSuchFieldException {
        switch (VoucherVariable.getVoucher(discountWay)) {
            case FIXED:
                var discountedAmount = beforeDiscount - discountValue;
                return (discountedAmount < 0) ? 0 : discountedAmount;
            case PERCENT:
                return beforeDiscount * (discountValue / 100);
        }
        throw new NoSuchFieldException("There is not available voucher");
    }

    public String toString() {
        try {
            return switch (VoucherVariable.getVoucher(discountWay)) {
                case FIXED ->
                        MessageFormat.format("voucher type -> {0}, voucherId -> {1}, Discount Amount -> {2}", discountWay, voucherId, discountValue);
                case PERCENT ->
                        MessageFormat.format("voucher type -> {0}, voucherId -> {1}, Discount Percentage -> {2}", discountWay, voucherId, discountValue);
            };
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }
}

