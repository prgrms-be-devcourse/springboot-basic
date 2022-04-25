package com.mountain.voucherApp.domain.vo;

import com.mountain.voucherApp.shared.constants.RegExp;
import org.springframework.util.Assert;

import java.util.Objects;

import static com.mountain.voucherApp.shared.constants.ErrorMessage.EMAIL_OUT_OF_RANGE;
import static com.mountain.voucherApp.shared.constants.ErrorMessage.NOT_BLANK;

public class Email {

    private final String address;
    private static final int MIN = 4;
    private static final int MAX = 50;

    public Email(String address) {
        Assert.notNull(address, NOT_BLANK);
        Assert.isTrue(address.length() >= MIN && address.length() <= MAX, EMAIL_OUT_OF_RANGE);
        Assert.isTrue(RegExp.EMAIL.validate(address), RegExp.EMAIL.getErrorMsg());
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return Objects.equals(address, email.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address);
    }
}
