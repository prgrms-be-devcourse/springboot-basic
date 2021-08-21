package org.prgrms.kdt.member.domain;

import org.prgrms.kdt.exception.ErrorMessage;
import org.prgrms.kdt.exception.InvalidArgumentException;

import java.util.regex.Pattern;

public class PhoneNumber {
    // Mobile Phone Identification Number :: 010
    private static final String PHONE_NUMBER_VALIDATOR = "^[0-9]{8}$";

    private final String phoneNumber;

    public PhoneNumber(String phoneNumber) {
        validate(phoneNumber);
        this.phoneNumber = phoneNumber;
    }

    public void validate(String phoneNumber) {
        if (!Pattern.matches(PHONE_NUMBER_VALIDATOR, phoneNumber)) {
            throw new InvalidArgumentException(ErrorMessage.PHONE_NUMBER);
        }
    }

    @Override
    public String toString() {
        return this.phoneNumber();
    }

    public String phoneNumber() {
        return phoneNumber;
    }

}