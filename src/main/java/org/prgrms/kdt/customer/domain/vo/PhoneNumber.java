package org.prgrms.kdt.customer.domain.vo;

import org.prgrms.kdt.exception.ErrorMessage;
import org.prgrms.kdt.exception.InvalidArgumentException;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

public class PhoneNumber {
    private static final String PHONE_NUMBER_VALIDATOR = "^010[0-9]{8}$";

    private String phoneNumber;

    public PhoneNumber(String phoneNumber) {
        validatePhoneNumber(phoneNumber);
        this.phoneNumber = phoneNumber;
    }

    public void validatePhoneNumber(String phoneNumber) {
        if (!StringUtils.hasText(phoneNumber) || !Pattern.matches(PHONE_NUMBER_VALIDATOR, phoneNumber)) {
            throw new InvalidArgumentException(ErrorMessage.PHONE_NUMBER);
        }
    }

    @Override
    public String toString() {
        return phoneNumber;
    }

}