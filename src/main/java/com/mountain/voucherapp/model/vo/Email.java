package com.mountain.voucherapp.model.vo;

import com.mountain.voucherapp.common.constants.RegExp;
import org.springframework.util.Assert;

import java.util.Objects;

import static com.mountain.voucherapp.common.constants.ErrorMessage.EMAIL_OUT_OF_RANGE;
import static com.mountain.voucherapp.common.constants.ErrorMessage.NOT_BLANK;

public class Email {

    private static final int MIN = 4;
    private static final int MAX = 50;
    private final String address;

    public Email(String address) {
        validate(address);
        this.address = address;
    }

    private void validate(String address) {
        Assert.notNull(address, NOT_BLANK.getMessage());
        Assert.isTrue(address.length() >= MIN && address.length() <= MAX, EMAIL_OUT_OF_RANGE.getMessage());
        Assert.isTrue(RegExp.EMAIL.validate(address), RegExp.EMAIL.getErrorMsg());
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
