package org.prgrms.kdt.domain.customer;

import org.prgrms.kdt.error.Message;
import org.prgrms.kdt.error.ValidationException;

import java.util.Objects;
import java.util.regex.Pattern;

public class Email {

    private static final String PATTERN = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";

    private final String email;

    public Email(String email) {
        validate(email);
        this.email = email;
    }

    public static Email valueOf(String email) {
        return new Email(email);
    }

    public String email() {
        return email;
    }

    public void validate(String email) {
        if (!Pattern.matches(PATTERN, email)) {
            throw new ValidationException(Message.WRONG_FORMAT_MESSAGE);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email1 = (Email) o;
        return Objects.equals(email(), email1.email());
    }

    @Override
    public int hashCode() {
        return Objects.hash(email());
    }

    @Override
    public String toString() {
        return "Email{" +
                "email='" + email + '\'' +
                '}';
    }
}
