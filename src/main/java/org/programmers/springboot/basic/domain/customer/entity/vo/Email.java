package org.programmers.springboot.basic.domain.customer.entity.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.programmers.springboot.basic.domain.customer.exception.IllegalEmailException;

import java.util.regex.Pattern;

@Getter
@RequiredArgsConstructor
public class Email {

    private static final String REGEX = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
    private static final Pattern pattern = Pattern.compile(REGEX);

    private final String email;

    public static Email valueOf(String email) {
        return new Email(email);
    }

    public void validate() {
        if (!pattern.matcher(email).matches()) {
            throw new IllegalEmailException();
        }
    }
}
