package org.prgrms.springbootbasic.engine.util;

import org.prgrms.springbootbasic.engine.enumtype.ErrorCode;
import org.prgrms.springbootbasic.exception.InvalidInputFormatException;

import java.util.UUID;

public class UUIDUtil {
    public static UUID convertStringToUUID(String stringId) {
        UUID id;
        try {
            id = UUID.fromString(stringId);
        }catch (IllegalArgumentException ex) {
            throw new InvalidInputFormatException("Invalid Id format.", ErrorCode.INVALID_UUID_FORMAT);
        }
        return id;
    }
}
