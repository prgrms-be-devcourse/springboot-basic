package org.devcourse.springbasic.validator;

import org.devcourse.springbasic.util.RegexPattern;
import java.util.regex.Matcher;

public class MenuValidator<T> implements Validator<T> {

    @Override
    public boolean validate(T valueToVerify) {

        if (valueToVerify instanceof String) {
            Matcher matcher = RegexPattern.VOUCHER_MENU_PATTERN
                    .matcher(valueToVerify.toString());

            return matcher.find();
        }

        return false;
    }

}
