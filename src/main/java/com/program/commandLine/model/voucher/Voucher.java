package com.program.commandLine.model.voucher;

import java.util.UUID;

public interface Voucher {

    UUID getVoucherId();

    VoucherType getVoucherType();

    int getVoucherDiscount();

    boolean getUsed();

    int discountPrice(int beforeDiscount);

    void used();

}
