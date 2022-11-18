package com.program.commandLine.voucher;

import java.util.UUID;

public interface Voucher {

    UUID getVoucherId();

    VoucherType getVoucherType();

    int getVoucherDiscount();

    UUID getAssignedCustomerId();

    boolean getUsed();

    int discountPrice(int beforeDiscount);

    void used();

    void assignCustomer(UUID customerId);

    void retrieved();
}
