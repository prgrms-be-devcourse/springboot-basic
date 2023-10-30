package team.marco.voucher_management_system.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import team.marco.voucher_management_system.type_enum.VoucherType;

class FixedAmountVoucherTest {
    private static final VoucherType FIXED_VOUCHER_TYPE = VoucherType.FIXED;
    private static final int MAXIMUM_AMOUNT = (int) 1e9;
    private static final int MINIMUM_AMOUNT = 1;

    @Test
    @DisplayName("올바른 파라미터를 넘겼을 때 바우처가 생성되야한다.")
    void testCreation() {
        // given
        int amount = 10_000;

        // when
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(amount);

        //then
        VoucherType voucherType = fixedAmountVoucher.getType();
        int data = fixedAmountVoucher.getData();

        assertThat(voucherType, is(FIXED_VOUCHER_TYPE));
        assertThat(data, is(amount));
    }

    @Test
    @DisplayName("생성 시 최소 할인 금액 보다 작을 경우 오류를 발생시켜야한다.")
    void testCreationLessThanMinimumAmount() {
        // given
        int lessThanMinimumAmount = MINIMUM_AMOUNT - 1;

        assertThat(lessThanMinimumAmount, greaterThanOrEqualTo(Integer.MIN_VALUE));

        // when
        // then
        assertThrows(IllegalArgumentException.class,
                () -> new FixedAmountVoucher(lessThanMinimumAmount));
    }

    @Test
    @DisplayName("생성 시 최소 할인 금액 보다 작을 경우 오류를 발생시켜야한다.")
    void testCreationGreaterThanMaximumAmount() {
        // given
        int greaterThanMaximumAmount = MAXIMUM_AMOUNT + 1;

        assertThat(greaterThanMaximumAmount, lessThanOrEqualTo(Integer.MAX_VALUE));

        // when
        // then
        assertThrows(IllegalArgumentException.class,
                () -> new FixedAmountVoucher(greaterThanMaximumAmount));
    }
}
