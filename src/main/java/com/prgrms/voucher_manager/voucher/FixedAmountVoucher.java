package com.prgrms.voucher_manager.voucher;

import com.prgrms.voucher_manager.exception.WrongVoucherValueException;
import com.prgrms.voucher_manager.voucher.controller.VoucherDto;
import lombok.Builder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Builder
public class FixedAmountVoucher implements Voucher {

    private final UUID voucherId;
    private long amount;

    private static final long MAX_AMOUNT = 10000L;
    private static final long MIN_AMOUNT = 0L;

    public FixedAmountVoucher(UUID id, long amount) {
        validateValue(amount);
        this.voucherId = id;
        this.amount = amount;
    }

    @Override
    public long discount(long beforeDiscount) {
        var discountedAmount = beforeDiscount - amount;
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
    public boolean validateType(String type) {
        VoucherType voucherType = VoucherType.getVoucherType(type);
        if(voucherType.toString().equals("FixedAmountVoucher")) return true;
        return false;
    }

    @Override
    public void changeValue(Long value) {
        validateValue(value);
        this.amount = value;
    }

    @Override
    public VoucherDto toVoucherDto() {
        return new VoucherDto(voucherId, "FixedAmountVoucher", amount);
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> hashMap = new HashMap<>() {{
            put("voucherId", voucherId.toString().getBytes());
            put("type", "fix");
            put("value", amount);
        }};
        return hashMap;
    }

    @Override
    public String toString() {
        return "type=FixedAmountVoucher" +
                ",id=" + voucherId +
                ",amount=" + amount +
                "\n";
    }
}
