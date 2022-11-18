package org.prgrms.kdt.voucher.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherAmount;
import org.prgrms.kdt.voucher.VoucherType;

import static org.assertj.core.api.Assertions.assertThat;

class VoucherMapperTest {

    private final VoucherMapper voucherMapper = new VoucherMapper();

    @DisplayName("타입과 amount는 바우처로 변환될 수 있다.")
    @ParameterizedTest
    @CsvSource(value = {"FIXED, 10", "FIXED, 1000", "FIXED, 10000", "FIXED, 0", "PERCENT, 10", "PERCENT, 20", "PERCENT, 100", "PERCENT, 0"})
    void fromMetaTest(String type, String amount) {
        // given

        // when
        Voucher actual = voucherMapper.fromMetadata(type, amount);

        // then
        assertThat(actual)
                .extracting(Voucher::getType, Voucher::getAmount)
                .containsExactly(VoucherType.of(type), new VoucherAmount(amount));

    }
}