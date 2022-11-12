package org.prgrms.kdt.domain;

import org.prgrms.kdt.exception.ErrorCode;
import org.prgrms.kdt.exception.InputException;
import org.prgrms.kdt.exception.IsNotNumberException;

import java.util.UUID;
import java.util.regex.Pattern;

public interface Voucher {
    String NUMBER_REGEX = "^[\\+\\-]?\\d+$";

    UUID getVoucherId();

    long discount(long beforeDiscount);

    void validate(String discountDegree);

    static void isNumeric(String number) {
        if (number == null) {
            throw new InputException(ErrorCode.InputException.getMessage());
        }
        if (!Pattern.matches(NUMBER_REGEX, number)) {
            throw new IsNotNumberException(ErrorCode.IS_NOT_NUMBER.getMessage());
        }
    }

}
