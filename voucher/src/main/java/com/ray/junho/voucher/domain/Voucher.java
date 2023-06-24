package com.ray.junho.voucher.domain;

import java.util.Currency;

public interface Voucher {
    Currency discount(Currency beforeDiscount);
}
