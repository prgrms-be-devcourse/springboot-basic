package org.prgrms.vouchermanager.domain.voucher;

import org.junit.jupiter.api.Test;
import org.prgrms.vouchermanager.exception.InputValueException;

import static org.junit.jupiter.api.Assertions.*;

class VoucherTypeTest {

    @Test
    void fromValue() {
        assertThrows(InputValueException.class, () -> VoucherType.fromValue("crcr"));

    }
}