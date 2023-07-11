package com.wonu606.vouchermanager.domain.customer;

import java.util.regex.Pattern;

public class EmailAddress {

    private static final Pattern ADDRESS_PATTERN = Pattern.compile(
            "^[A-Za-z0-9]+@[A-Za-z0-9-]+.[A-Za-z]+(.[A-Za-z]+)*$");

    private final String address;

    public EmailAddress(String address) {
        validateAddress(address);
        this.address = address;
    }

    private static boolean isNotAddressPattern(String address) {
        return !ADDRESS_PATTERN.matcher(address).matches();
    }

    public String getAddress() {
        return address;
    }

    private void validateAddress(String address) {
        if (isNotAddressPattern(address)) {
            throw new IllegalArgumentException("잘못된 이메일 주소입니다: " + address);
        }
    }

    @Override
    public String toString() {
        return "EmailAddress{" + "address='" + address + '\'' + '}';
    }
}
