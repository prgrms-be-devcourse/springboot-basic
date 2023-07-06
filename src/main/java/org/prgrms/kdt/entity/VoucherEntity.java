package org.prgrms.kdt.entity;

import org.prgrms.kdt.domain.voucher.Voucher;
import org.springframework.stereotype.Component;

import java.util.UUID;

public class VoucherEntity {

    private static UUID voucherId;
    private static String voucherType;
    private static Long amount;
    private static boolean status;

    public VoucherEntity(UUID voucherId, String voucherType, Long amount, boolean status) {
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.amount= amount;
        this.status = status;
    }

    public static VoucherEntity toEntity(Voucher voucher) {
        voucherId = voucher.getVoucherId();
        voucherType = String.valueOf(voucher.getVoucherType());
        amount = voucher.getDiscountAmount();
        status = voucher.getStatus();

        return new VoucherEntity(voucherId, voucherType, amount, status);
    }

    public static UUID getVoucherId() {
        return voucherId;
    }



    public static String getVoucherType() {
        return voucherType;
    }



    public static Long getAmount() {
        return amount;
    }


    public static boolean getStatus() {
        return status;
    }
}
