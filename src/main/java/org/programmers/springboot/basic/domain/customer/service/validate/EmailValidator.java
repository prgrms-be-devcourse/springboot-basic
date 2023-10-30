package org.programmers.springboot.basic.domain.customer.service.validate;

import org.programmers.springboot.basic.domain.customer.exception.IllegalEmailException;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class EmailValidator {
    
    private static final String REGEX = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
    private static final Pattern pattern = Pattern.compile(REGEX);

    public void validate(String email) {

        if (!pattern.matcher(email).matches()) {
            throw new IllegalEmailException();
        }
    }
}
