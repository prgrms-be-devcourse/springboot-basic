package org.prgrms.springbootbasic.engine.util;

import org.prgrms.springbootbasic.engine.enumtype.ErrorCode;
import org.prgrms.springbootbasic.exception.FieldNotBlankException;

public class GlobalUtil {
    public static void validateNotBlank(String input) {
        if (input.isBlank()) {
            throw new FieldNotBlankException("Name is required!", ErrorCode.FIELD_NOT_BLANK);
        }
    }
}
