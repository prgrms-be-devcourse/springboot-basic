package org.prgrms.voucherapp.engine;

import org.prgrms.voucherapp.exception.WrongAmountException;
import org.prgrms.voucherapp.global.VoucherType;

import java.text.MessageFormat;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    // TODO : MAX_VOUCHER_AMOUNT YAML 파일로 외부 설정으로 주입하기
//    private static final long MAX_VOUCHER_AMOUNT = 100000;
    private final UUID voucherId;
    private final long discountAmount;

    public FixedAmountVoucher(UUID voucherId, long discountAmount) {
        this.discountAmount = discountAmount;
        this.voucherId = voucherId;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        var discountedPrice = beforeDiscount - discountAmount;
        return (discountedPrice < 0) ? 0 : discountedPrice;
    }


    @Override
    public String toString() {
        return MessageFormat.format("TYPE : {0}, AMOUNT : {1}, ID : {2}", VoucherType.FIX.toString(), discountAmount, voucherId);
    }
}
