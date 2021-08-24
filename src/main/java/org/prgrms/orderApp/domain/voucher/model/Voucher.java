package org.prgrms.orderApp.domain.voucher.model;

import org.prgrms.orderApp.domain.order.model.OrderAppModel;

import java.util.UUID;


public interface Voucher extends OrderAppModel {
    UUID getVoucherId();
    long discount(long beforeDiscount);
    long getVoucherAmount();
}
