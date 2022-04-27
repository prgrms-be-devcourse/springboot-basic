package com.prgrms.voucher_manager.voucher;


import com.prgrms.voucher_manager.exception.WrongVoucherValueException;
import com.prgrms.voucher_manager.voucher.controller.VoucherDto;
import lombok.Builder;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Builder
public class PercentDiscountVoucher implements Voucher{

    private final UUID voucherId;
    private long percent;

    private static final long MAX_PERCENT = 100L;
    private static final long MIN_PERCENT = 0L;

    public PercentDiscountVoucher(UUID id, long percent) {
        validateValue(percent);
        this.voucherId = id;
        this.percent = percent;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - (long) (beforeDiscount * ((double) percent / 100));
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public void validateValue(Long percent) {
        if(percent < MIN_PERCENT || percent > MAX_PERCENT)
            throw new WrongVoucherValueException("0 ~ 100 사이의 수를 입력해야 합니다.");
    }

    @Override
    public boolean validateType(String type) {
        VoucherType voucherType = VoucherType.getVoucherType(type);
        if(voucherType.toString().equals("PercentDiscountVoucher")) return true;
        return false;
    }

    @Override
    public void changeValue(Long value) {
        validateValue(value);
        this.percent = value;
    }

    @Override
    public VoucherDto toVoucherDto() {
        return new VoucherDto(voucherId, "PercentDiscountVoucher", percent);
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> hashMap = new HashMap<>() {{
            put("voucherId", voucherId.toString().getBytes());
            put("type", "percent");
            put("value", percent);
        }};
        System.out.println(hashMap.get("value"));
        return hashMap;
    }

    @Override
    public String toString() {
        return "type=PercentDiscountVoucher" +
                ",id=" + voucherId +
                ",percent=" + percent +
                "\n";
    }
}
