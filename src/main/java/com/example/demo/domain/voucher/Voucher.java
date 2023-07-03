package com.example.demo.domain.voucher;

import java.util.UUID;

public interface Voucher {

    double discount(double beforeAmount);

    UUID getId();

    double getDiscountAmount();
}
