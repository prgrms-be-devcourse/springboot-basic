package com.program.commandLine.voucher;

import com.program.commandLine.customer.Customer;

import java.util.Optional;
import java.util.UUID;

public interface Voucher {

    UUID getVoucherId();

    VoucherType getVoucherType();

    int getVoucherDiscount();

    UUID getAssignedCustomerId();

    boolean getUsing();

    int discountPrice(int beforeDiscount);

}
