package org.prgrms.springbootbasic.engine.util;

import org.prgrms.springbootbasic.engine.enumtype.ErrorCode;
import org.prgrms.springbootbasic.engine.enumtype.VoucherType;
import org.prgrms.springbootbasic.exception.FieldNotBlankException;
import org.prgrms.springbootbasic.exception.InvalidInputFormatException;

public class GlobalUtil {
    public static void validateNotBlank(String input) {
        if (input.isBlank()) {
            throw new FieldNotBlankException("Name is required!", ErrorCode.FIELD_NOT_BLANK);
        }
    }

    public static VoucherType convertStringToVoucherType(String input) {
        try {
            return VoucherType.valueOf(input);
        } catch (IllegalArgumentException ex) {
            throw new InvalidInputFormatException("Invalid voucher type.", ErrorCode.INVALID_VOUCHER_TYPE);
        }
    }
}
