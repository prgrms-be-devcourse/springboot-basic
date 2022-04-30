package org.prgrms.spring_week1.Voucher.model;

import java.time.LocalDateTime;
import java.util.UUID;
import org.apache.tomcat.jni.Local;

public interface Voucher {

    long discount(long beforeDiscount);

    UUID getVoucherId();

    UUID getCustomerId();

    VoucherType getVoucherType();

    VoucherStatus getVoucherStatus();

    long getDiscount();

    LocalDateTime getCreatedAt();

    LocalDateTime getUpdatedAt();


    void setStatus(VoucherStatus status);
}
