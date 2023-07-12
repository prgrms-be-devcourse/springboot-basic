package com.wonu606.vouchermanager.domain.customer;

import com.wonu606.vouchermanager.domain.customer.emailAddress.EmailAddress;
import java.util.Objects;

public class Customer {

    private final EmailAddress emailAddress;
    private final String nickname;

    public Customer(EmailAddress emailAddress, String nickname) {
        this.emailAddress = emailAddress;
        this.nickname = nickname;
    }

    public String getEmailAddress() {
        return emailAddress.getAddress();
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Customer customer = (Customer) o;
        return Objects.equals(getEmailAddress(), customer.getEmailAddress())
                && Objects.equals(getNickname(), customer.getNickname());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmailAddress(), getNickname());
    }

    @Override
    public String toString() {
        return "Customer{" +
                "emailAddress=" + emailAddress +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
