package org.prgms.kdtspringweek1.voucher.service.dto;

import org.prgms.kdtspringweek1.voucher.entity.Voucher;
import org.prgms.kdtspringweek1.voucher.entity.VoucherType;

import java.text.MessageFormat;
import java.util.UUID;

public class FindVoucherResponseDto {
    private final UUID voucherId;
    private final long discountValue;
    private final VoucherType voucherType;

    public FindVoucherResponseDto(Voucher voucher) {
        this.voucherId = voucher.getVoucherId();
        this.discountValue = voucher.getDiscountValue();
        this.voucherType = voucher.getVoucherType();
    }

    public void printVoucherInfo() {
        System.out.println("--------------------------------------------------");
        System.out.println(MessageFormat.format("Voucher Type: {0}", voucherType.getName()));
        System.out.println(MessageFormat.format("Voucher Id: {0}", voucherId));
        System.out.println(MessageFormat.format("Discount: {0}", discountValue));
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public long getDiscountValue() {
        return discountValue;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }
}
