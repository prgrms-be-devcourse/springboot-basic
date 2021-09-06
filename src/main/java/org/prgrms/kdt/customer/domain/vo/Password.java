package org.prgrms.kdt.customer.domain.vo;

import org.prgrms.kdt.exception.ErrorMessage;
import org.prgrms.kdt.exception.InvalidArgumentException;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.regex.Pattern;

public class Password {
    private static final String PASSWORD_VALIDATOR = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[가-힣A-Za-z\\d$@$!%*#?&]{8,30}$";

    private String password;

    public Password(String password) {
        validatePassword(password);
        this.password = password;
    }

    public void validatePassword(String password) {
        if (!StringUtils.hasText(password) || !Pattern.matches(PASSWORD_VALIDATOR, password)) {
            throw new InvalidArgumentException(ErrorMessage.PASSWORD);
        }
    }

    @Override
    public String toString() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Password password1 = (Password) o;
        return Objects.equals(password, password1.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password);
    }
}
