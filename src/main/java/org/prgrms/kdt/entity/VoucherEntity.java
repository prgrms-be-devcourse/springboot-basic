package org.prgrms.kdt.entity;

import org.prgrms.kdt.domain.voucher.Voucher;
import org.springframework.stereotype.Component;

import java.util.UUID;

public class VoucherEntity {

    private static Long voucherId;
    private static String voucherType;
    private static Long amount;
    private static boolean status;

    public VoucherEntity(Long voucherId, String voucherType, Long amount, boolean status) {
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

    public Long getVoucherId() {
        return voucherId;
    }



    public String getVoucherType() {
        return voucherType;
    }



    public Long getAmount() {
        return amount;
    }


    public boolean getStatus() {
        return status;
    }
}
