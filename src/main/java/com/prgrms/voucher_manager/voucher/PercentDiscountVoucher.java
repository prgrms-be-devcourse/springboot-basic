package com.prgrms.voucher_manager.voucher;


import com.prgrms.voucher_manager.exception.WrongVoucherValueException;
import com.prgrms.voucher_manager.voucher.controller.VoucherDto;
import lombok.Builder;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Builder
public class PercentDiscountVoucher implements Voucher{

    private final UUID voucherId;
    private long value;
    private final LocalDate createdAt;
    private String type;

    private static final long MAX_PERCENT = 100L;
    private static final long MIN_PERCENT = 0L;

    public PercentDiscountVoucher(UUID id, long percent, LocalDate createdAt) {
        validateValue(percent);
        this.voucherId = id;
        this.value = percent;
        this.createdAt = createdAt;
        this.type = "PercentDiscountVoucher";
    }

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        validateValue(percent);
        this.voucherId = voucherId;
        this.value = percent;
        createdAt = LocalDate.now();
        this.type = "PercentDiscountVoucher";
    }

    public PercentDiscountVoucher(UUID voucherId, long value, LocalDate createdAt, String type) {
        this.voucherId = voucherId;
        this.value = value;
        this.createdAt = createdAt;
        this.type = "PercentDiscountVoucher";
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - (long) (beforeDiscount * ((double) value / 100));
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
    public boolean isValidDate(LocalDate start, LocalDate end) {
        if(createdAt.compareTo(start) > 0 && createdAt.compareTo(end) < 0) return true;
        else return createdAt.equals(start) || createdAt.equals(end);
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
        this.value = value;
    }

    @Override
    public VoucherDto toVoucherDto() {
        return new VoucherDto(voucherId, "PercentDiscountVoucher", value, createdAt);
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> hashMap = new HashMap<>() {{
            put("voucherId", voucherId.toString().getBytes());
            put("type", "percent");
            put("value", value);
            put("createdAt", createdAt);
        }};
        System.out.println(hashMap.get("value"));
        return hashMap;
    }

    @Override
    public String toString() {
        return "type=PercentDiscountVoucher" +
                ",id=" + voucherId +
                ",percent=" + value +
                ",createdAt=" + createdAt +
                "\n";
    }
}
