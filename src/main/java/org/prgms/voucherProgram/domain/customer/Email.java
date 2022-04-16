package org.prgms.voucherProgram.domain.customer;

import java.util.Objects;
import java.util.regex.Pattern;

import org.prgms.voucherProgram.exception.WrongEmailException;

public class Email {
    private static final int MAX_EMAIL_LENGTH = 50;
    private static final String ERROR_EMAIL_BLANK_MESSAGE = "[ERROR] 이메일이 비어있습니다.";
    private static final String ERROR_EMAIL_LENGTH_OVER_MAX_LENGTH = "[ERROR] 이메일은 50자 이상을 넘을 수 없습니다.";
    private static final String ERROR_WRONG_EMAIL_MESSAGE = "[ERROR] 이메일 형식이 잘못되었습니다.";
    private static final Pattern EMAIL = Pattern.compile(
        "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$");

    private final String email;

    public Email(String email) {
        validateEmail(email);
        this.email = email;
    }

    private void validateEmail(String email) {
        if (email.isBlank()) {
            throw new WrongEmailException(ERROR_EMAIL_BLANK_MESSAGE);
        }
        if (isWrongEmail(email)) {
            throw new WrongEmailException(ERROR_WRONG_EMAIL_MESSAGE);
        }
        if (email.length() > MAX_EMAIL_LENGTH) {
            throw new WrongEmailException(ERROR_EMAIL_LENGTH_OVER_MAX_LENGTH);
        }
    }

    private boolean isWrongEmail(String email) {
        return !EMAIL.matcher(email).matches();
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Email otherEmail = (Email)o;
        return this.email.equals(otherEmail.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail());
    }
}
