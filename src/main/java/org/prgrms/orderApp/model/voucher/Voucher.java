package org.prgrms.orderApp.model.voucher;

import org.prgrms.orderApp.model.OrderAppModel;

import java.util.UUID;


public interface Voucher extends OrderAppModel {
    UUID getVoucherId();
    long discount(long beforeDiscount);
}
