package com.programmers.springbootbasic.domain.voucher.entity;

import java.io.Serializable;
import java.util.UUID;


public interface Voucher extends Serializable {

    UUID getVoucherId();

    long getValue();

    long discount(long beforeDiscount);

    String getInformation();

}