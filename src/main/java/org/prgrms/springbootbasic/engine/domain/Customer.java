package org.prgrms.springbootbasic.engine.domain;

import com.opencsv.bean.CsvBindByName;
import org.prgrms.springbootbasic.engine.enumtype.ErrorCode;
import org.prgrms.springbootbasic.exception.InvalidInputFormatException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;

import static org.prgrms.springbootbasic.engine.util.GlobalUtil.validateNotBlank;

public class Customer {
    @CsvBindByName
    private final UUID customerId;
    @CsvBindByName
    private String name;
    @CsvBindByName
    private final String email;
    @CsvBindByName
    private LocalDateTime lastLoginAt;
    @CsvBindByName
    private final LocalDateTime createdAt;
    private Boolean isBlack;

    public Customer(UUID customerId, String name, String email, LocalDateTime lastLoginAt, LocalDateTime createdAt) {
        validateNotBlank(name);
        validateEmail(email);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
        this.createdAt = createdAt;
        this.isBlack = false;
    }

    public Customer(UUID customerId, String name, String email, LocalDateTime createdAt) {
        validateNotBlank(name);
        validateEmail(email);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.isBlack = false;
    }

    public void changeName(String name) {
        validateNotBlank(name);
        this.name = name;
    }

    public void toBlack() {
        this.isBlack = true;
    }

    public void revokeBlack() {
        this.isBlack = false;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getIsBlack() {return isBlack;}

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

    private void validateEmail(String email) {
        if (!Pattern.matches("\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b", email)) {
            throw new InvalidInputFormatException("Invalid Email format", ErrorCode.INVALID_EMAIL_FORMAT);
        }
    }
}
