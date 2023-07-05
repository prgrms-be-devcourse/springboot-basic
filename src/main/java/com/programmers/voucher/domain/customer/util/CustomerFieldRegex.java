package com.programmers.voucher.domain.customer.util;

public final class CustomerFieldRegex {
    public static final String nameRegex = "^[A-Za-z0-9]{1,20}";
    public static final String emailRegex = "^[A-Za-z0-9._%+-]{1,20}@[A-Za-z0-9.-]{1,20}\\.[A-Za-z]{2,3}$";

    private CustomerFieldRegex() {
        
    }
}
