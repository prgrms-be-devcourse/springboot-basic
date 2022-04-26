package com.example.voucher_manager.io;

import com.example.voucher_manager.domain.voucher.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class InputValidator {
    // log info
    private static final Logger log = LoggerFactory.getLogger(InputValidator.class);
    private static final String INVALID_VOUCHER_TYPE_ERROR = "I/O ERROR : Invalid Voucher Type Error";
    private static final String INVALID_COMMAND_ERROR = "I/O ERROR : Invalid Command Error";
    private static final String IS_NOT_INTEGER = "I/O ERROR : Input is not Integer Error";
    private static final String PERCENT_OUT_OF_RANGE = "I/O ERROR : Percent Out of range Error";
    private static final String IS_NOT_POSITIVE_NUMBER = "I/O ERROR : Input is not Positive Number";

    // about percent voucher
    private static final Long MIN_PERCENT = 1L;
    private static final Long MAX_PERCENT = 100L;

    // about fixed voucher
    private static final Long MIN_DISCOUNT_AMOUNT = 1L;

    // about discount information
    private static final String INTEGER_PATTERN = "-?\\d+";

    // about customer service
    private static final String OVER_NAME_LENGTH = "I/O ERROR : Name is less than 20 characters.";
    private static final String INVALID_EMAIL_FORMAT = "I/O ERROR : Invalid Email Format.";
    private static final String EMAIL_PATTERN = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    public VoucherType validateVoucherType(String type) {
        if (VoucherType.isValidated(type)) {
            return VoucherType.of(type);
        }
        log.error(INVALID_VOUCHER_TYPE_ERROR);
        return VoucherType.ERROR;
    }

    public CommandType validateCommandType(String command) {
        if (CommandType.isValidated(command)){
            return CommandType.of(command);
        }
        log.error(INVALID_COMMAND_ERROR);
        return CommandType.ERROR;
    }

    public boolean isInteger(String input) {
        if (input.matches(INTEGER_PATTERN)) {
            return true;
        }
        log.error(IS_NOT_INTEGER);
        return false;
    }

    public boolean isCorrectRangeOfPercent(Long percent) {
        if (percent >= MIN_PERCENT && percent <= MAX_PERCENT) {
            return true;
        }
        log.error(PERCENT_OUT_OF_RANGE);
        return false;
    }

    public boolean isPositiveNumber(Long amount) {
        if (amount >= MIN_DISCOUNT_AMOUNT) {
            return true;
        }
        log.error(IS_NOT_POSITIVE_NUMBER);
        return false;
    }

    public boolean isCorrectLengthOfName(String name) {
        if (name.length() <= 20){
            return true;
        }
        log.error(OVER_NAME_LENGTH);
        return false;
    }

    public boolean isValidEmailFormat(String email){
        if (email.matches(EMAIL_PATTERN)) {
            return true;
        }
        log.error(INVALID_EMAIL_FORMAT);
        return false;
    }
}
