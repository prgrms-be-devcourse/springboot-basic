package com.prgrms.voucher_manager.voucher;

import com.prgrms.voucher_manager.exception.WrongVoucherValueException;
import com.prgrms.voucher_manager.voucher.controller.VoucherDto;
import lombok.Builder;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Builder
public class FixedAmountVoucher implements Voucher {

    private final UUID voucherId;
    private long value;
    private String type;
    private final LocalDate createdAt;

    private static final long MAX_AMOUNT = 10000L;
    private static final long MIN_AMOUNT = 0L;

    public FixedAmountVoucher(UUID voucherId, long value, String type, LocalDate createdAt) {
        this.voucherId = voucherId;
        this.value = value;
        this.type = "FixedAmountVoucher";
        this.createdAt = createdAt;
    }

    public FixedAmountVoucher(UUID id, long amount, LocalDate createdAt) {
        validateValue(amount);
        this.voucherId = id;
        this.value = amount;
        this.createdAt = createdAt;
        this.type = "FixedAmountVoucher";
    }

    public FixedAmountVoucher(UUID voucherId, long amount) {
        validateValue(amount);
        this.voucherId = voucherId;
        this.value = amount;
        createdAt = LocalDate.now();
        this.type = "FixedAmountVoucher";
    }

    @Override
    public long discount(long beforeDiscount) {
        var discountedAmount = beforeDiscount - value;
        return (discountedAmount < 0 ) ? 0 : discountedAmount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public void validateValue(Long amount) {
        if(amount > MAX_AMOUNT || amount < MIN_AMOUNT)
            throw new WrongVoucherValueException("0 ~ 10,000 사이의 수를 입력해야 합니다.");

    }

    @Override
    public boolean isValidDate(LocalDate start, LocalDate end) {
        if(createdAt.compareTo(start) > 0 && createdAt.compareTo(end) < 0) return true;
        else return createdAt.equals(start) || createdAt.equals(end);
    }

    @Override
    public boolean validateType(String type) {
        VoucherType voucherType = VoucherType.getVoucherType(type);
        if(voucherType.toString().equals("FixedAmountVoucher")) return true;
        return false;
    }

    @Override
    public void changeValue(Long value) {
        validateValue(value);
        this.value = value;
    }

    @Override
    public VoucherDto toVoucherDto() {
        return new VoucherDto(voucherId, "FixedAmountVoucher", value, createdAt);
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> hashMap = new HashMap<>() {{
            put("voucherId", voucherId.toString().getBytes());
            put("type", "fix");
            put("value", value);
            put("createdAt", createdAt);
        }};
        return hashMap;
    }

    @Override
    public String toString() {
        return "type=FixedAmountVoucher" +
                ",id=" + voucherId +
                ",amount=" + value +
                ",createdAt=" + createdAt +
                "\n";
    }
}
