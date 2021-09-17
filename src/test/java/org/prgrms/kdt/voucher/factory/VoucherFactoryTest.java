package org.prgrms.kdt.voucher.factory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.VoucherType;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class VoucherFactoryTest {
    @Test
    @DisplayName("인스턴스를 뱉을 수 있어야 한다.")
    void testGetInstance() {
        // GIVEN
        VoucherFactory instance = VoucherFactory.getInstance();

        // THEN
        assertThat(VoucherFactory.getInstance()) // when
                .isEqualTo(instance); // then
    }

    @Test
    @DisplayName("FIXED Type, Percent Type 종류의 바우처를 만들 수 있다.")
    void testCreateVoucher() {
        // GIVEN
        VoucherFactory instance = VoucherFactory.getInstance();

        // FIXED TEST
        assertThat(
                instance.createVoucher( // when
                        VoucherType.FIXED,
                        UUID.randomUUID(),
                        100
                )
        ).isInstanceOf(FixedAmountVoucher.class); // then

        // PERCENT TEST
        assertThat(
                instance.createVoucher(
                        VoucherType.PERCENTAGE,
                        UUID.randomUUID(),
                        40
                )
        ).isInstanceOf(PercentDiscountVoucher.class);
    }

    @Test
    @DisplayName("FIXED/PERCENT 를 제외한 타입을 생성하는 경우 예외를 뱉는다. 퉤 ")
    void testCreateVoucherExcludedTypeException() {
        // GIVEN
        VoucherFactory instance = VoucherFactory.getInstance();
        String errorMsg = "Can't find voucher type";

        // FIXED TEST
        assertThatThrownBy(() -> {
            instance.createVoucher(
                    null, // 이런 경우가 있을까?
                    UUID.randomUUID(),
                    100
            );
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage(errorMsg);

    }
}
