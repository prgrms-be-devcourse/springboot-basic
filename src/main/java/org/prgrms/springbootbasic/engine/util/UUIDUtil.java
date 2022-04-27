package org.prgrms.springbootbasic.engine.util;

import org.prgrms.springbootbasic.engine.domain.Voucher;
import org.prgrms.springbootbasic.exception.VoucherException;

import java.util.UUID;

public class UUIDUtil {
    public static UUID convertStringToUUID(String stringId) {
        UUID id;
        try {
            id = UUID.fromString(stringId);
        }catch (IllegalArgumentException ex) {
            throw new VoucherException("Invalid Id format");
        }
        return id;
    }
}
