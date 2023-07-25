package com.wonu606.vouchermanager.domain.customer;

import com.wonu606.vouchermanager.domain.customer.email.Email;
import java.util.Objects;

public class Customer {

    private final Email email;
    private final String nickname;

    public Customer(Email email, String nickname) {
        this.email = email;
        this.nickname = nickname;
    }

    public String getEmailAddress() {
        return email.getAddress();
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
                "emailAddress=" + email +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
