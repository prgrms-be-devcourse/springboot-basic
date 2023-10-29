package com.programmers.springbootbasic.domain.voucher.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;


public interface Voucher extends Serializable {

    UUID getVoucherId();

    long getValue();

    LocalDate getCreatedAt();

    long discount(long beforeDiscount);

    String getInformation();

}