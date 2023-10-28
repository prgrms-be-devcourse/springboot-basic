package org.programmers.springboot.basic.domain.customer.service.validate;

import org.programmers.springboot.basic.domain.customer.exception.IllegalEmailException;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class EmailValidator {

    public void validate(String email) {

        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern pattern = Pattern.compile(regex);
        if (!pattern.matcher(email).matches()) {
            throw new IllegalEmailException();
        }
    }
}
