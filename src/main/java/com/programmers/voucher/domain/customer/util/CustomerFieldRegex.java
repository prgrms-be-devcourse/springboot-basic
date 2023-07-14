package com.programmers.voucher.domain.customer.util;

import java.util.regex.Pattern;

public final class CustomerFieldRegex {
    public static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z0-9]{1,20}");
    public static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]{1,20}@[A-Za-z0-9.-]{1,20}\\.[A-Za-z]{2,3}$");


    private CustomerFieldRegex() {
        
    }
}
