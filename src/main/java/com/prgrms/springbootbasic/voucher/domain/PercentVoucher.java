package com.prgrms.springbootbasic.voucher.domain;

import static com.prgrms.springbootbasic.common.exception.ExceptionMessage.ILLEGAL_STATE_EXCEPTION_WHEN_DISCOUNT;

import com.prgrms.springbootbasic.common.exception.AmountOutOfBoundException;

import java.math.BigDecimal;

import com.prgrms.springbootbasic.voucher.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class PercentVoucher implements Voucher {

    private static final int MAX_AMOUNT_BOUNDARY = 99;
    private static final int MIN_AMOUNT_BOUNDARY = 1;
    private static final Logger logger = LoggerFactory.getLogger(PercentVoucher.class);

    private final UUID id;
    private int percent;
    private VoucherType voucherType;

    public PercentVoucher(int percent) {
        validate(percent);
        this.id = UUID.randomUUID();
        this.percent = percent;
    }

    public PercentVoucher(UUID uuid, int percent) {
        validate(percent);
        this.id = uuid;
        this.percent = percent;
    }

    public PercentVoucher(UUID uuid, int percent, VoucherType voucherType) {
        this(uuid, percent);
        this.voucherType = voucherType;
    }

    public PercentVoucher(int percent, VoucherType voucherType) {
        this(percent);
        this.voucherType = voucherType;
    }

    @Override
    public void validate(int discountAmount) {
        if (discountAmount < MIN_AMOUNT_BOUNDARY || discountAmount > MAX_AMOUNT_BOUNDARY) {
            logger.warn("AmountOutOfBoundException occurred when creating new Voucher. Amount dut of boundary.");
            throw new AmountOutOfBoundException(this.getClass().getSimpleName(), MIN_AMOUNT_BOUNDARY, MAX_AMOUNT_BOUNDARY);
        }
    }

    @Override
    public UUID getUUID() {
        return id;
    }

    @Override
    public int getDiscountAmount() {
        return percent;
    }

    @Override
    public VoucherType getVoucherType() {
        return voucherType;
    }

    @Override
    public BigDecimal discount(int beforeDiscount) {
        BigDecimal afterDiscount = new BigDecimal(beforeDiscount * (1 - (percent / 100)));
        if (afterDiscount.compareTo(BigDecimal.ZERO) <= -1) {
            throw new IllegalStateException(ILLEGAL_STATE_EXCEPTION_WHEN_DISCOUNT);
        }
        return afterDiscount;
    }

    @Override
    public void update(int discountAmount) {
        validate(discountAmount);
        this.percent = discountAmount;
    }
}
