package org.prgrms.spring_week1.customer.model;

import java.util.Objects;
import java.util.regex.Pattern;
import org.springframework.util.Assert;

public class PhoneNumber {
    private final String number;

    public PhoneNumber(String number) {
        Assert.isNull(number, "Phone Number should not be null");
        Assert.isTrue(checkPhoneNumber(number), "Invalid Phone Number");
        this.number = number;
    }

    private boolean checkPhoneNumber(String number) {
        return Pattern.matches("/^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/", number);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PhoneNumber that = (PhoneNumber) o;
        return Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}
