package org.prgrms.kdt.command.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.voucher.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

class CreateCommandServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(CreateCommandServiceTest.class);

    @Test
    @DisplayName("enum의 toString 와 name는 같지 않아야 한다.")
    void testEnumString() {
        // given
        VoucherType voucherType = VoucherType.FIXED;

        // when
        String nameValue = voucherType.name();
        String toStringValue = voucherType.toString();

        CreateCommandServiceTest.logger.error("name() : {}, toSting() : {}", nameValue, VoucherType.FIXED);

        // then
        assertThat(nameValue).isNotEqualTo(toStringValue);
    }
}