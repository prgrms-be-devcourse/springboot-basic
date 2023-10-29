package org.prgrms.prgrmsspring.entity.user;

import org.prgrms.prgrmsspring.exception.ExceptionMessage;

import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;


public class Customer {
    private final UUID customerId;
    private final String name;
    private final String email;
    private final Boolean isBlack;

    private static final String NAME_REGEX = "^[a-zA-Z가-힣]*$"; // 영어 또는 한글만 허용하는 정규식
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"; // 이메일 형식을 검증하는 정규식

private static final Pattern NAME_PATTERN = Pattern.compile(NAME_REGEX);
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public Customer(UUID customerId, String name, String email) {
        this(customerId, name, email, false);
    }

    public Customer(UUID customerId, String name, String email, Boolean isBlack) {
        if (!NAME_PATTERN.matcher(name).matches()) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_NAME_INPUT.getMessage());
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_EMAIL_INPUT.getMessage());
        }
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.isBlack = isBlack;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public Boolean getIsBlack() {
        return isBlack;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Customer customer = (Customer) object;
        return Objects.equals(customerId, customer.customerId) && Objects.equals(name, customer.name) && Objects.equals(email, customer.email) && Objects.equals(isBlack, customer.isBlack);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, name, email, isBlack);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", isBlack=" + isBlack +
                '}';
    }
}
