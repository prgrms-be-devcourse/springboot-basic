package com.programmers.voucher.domain.customer.domain;

import com.programmers.voucher.domain.customer.util.CustomerErrorMessages;
import com.programmers.voucher.domain.customer.util.CustomerFieldRegex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.UUID;
import java.util.regex.Matcher;

public class Customer {
    private static final Logger LOG = LoggerFactory.getLogger(Customer.class);

    private final UUID customerId;
    private final String email;
    private String name;
    private boolean banned = false;

    public Customer(UUID customerId, String email, String name) {
        validateEmail(email);
        validateName(name);
        this.customerId = customerId;
        this.email = email;
        this.name = name;
    }

    private void validateEmail(String email) {
        Matcher emailMatcher = CustomerFieldRegex.EMAIL_PATTERN.matcher(email);
        if (!emailMatcher.matches()) {
            String errorMessage = MessageFormat.format(CustomerErrorMessages.INVALID_EMAIL, email);
            LOG.warn(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
    }

    private void validateName(String name) {
        Matcher nameMatcher = CustomerFieldRegex.NAME_PATTERN.matcher(name);
        if (!nameMatcher.matches()) {
            String errorMessage = MessageFormat.format(CustomerErrorMessages.INVALID_NAME, name);
            LOG.warn(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public void update(String name, boolean banned) {
        validateName(name);
        this.name = name;
        this.banned = banned;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public boolean isBanned() {
        return banned;
    }
}
