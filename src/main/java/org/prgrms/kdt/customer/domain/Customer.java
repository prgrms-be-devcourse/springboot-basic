package org.prgrms.kdt.customer.domain;

import org.prgrms.kdt.common.Date;
import org.prgrms.kdt.customer.domain.vo.*;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer extends Date {
    private UUID customerId;
    private Email email;
    private Password password;
    private Name name;
    private PhoneNumber phoneNumber;
    private Role role;

    public Customer(LocalDateTime createdDate, LocalDateTime modifiedDate, UUID customerId, Email email, Password password, Name name, PhoneNumber phoneNumber, Role role) {
        super(createdDate, modifiedDate);
        this.customerId = customerId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public Email getEmail() {
        return email;
    }

    public Password getPassword() {
        return password;
    }

    public Name getName() {
        return name;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public Role getRole() {
        return role;
    }

    public Customer isBlackList() {
        role = role == Role.USER ? Role.BLACKLIST : Role.USER;
        return this;
    }
}
