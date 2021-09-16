package org.prgrms.kdt.customer.dto;

import org.prgrms.kdt.customer.domain.*;
import org.prgrms.kdt.customer.domain.vo.*;

import java.time.LocalDateTime;
import java.util.UUID;

public class CustomerSignDto {
    private Email email;
    private Password password;
    private Name name;
    private PhoneNumber phoneNumber;

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public Password getPassword() {
        return password;
    }

    public void setPassword(Password password) {
        this.password = password;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
