package team.marco.voucher_management_system.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import team.marco.voucher_management_system.type_enum.VoucherType;

class PercentDiscountVoucherTest {
    private static final VoucherType PERCENT_VOUCHER_TYPE = VoucherType.PERCENT;
    private static final int MAXIMUM_PERCENT = 100;
    private static final int MINIMUM_PERCENT = 1;

    @Test
    @DisplayName("올바른 파라미터를 넘겼을 때 바우처가 생성되야한다.")
    void testCreation() {
        // given
        int percent = 50;

        // when
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(percent);

        //then
        VoucherType voucherType = percentDiscountVoucher.getType();
        int data = percentDiscountVoucher.getData();

        assertThat(voucherType, is(PERCENT_VOUCHER_TYPE));
        assertThat(data, is(percent));
    }

    @Test
    @DisplayName("생성 시 최소 할인 퍼센트 보다 작을 경우 오류를 발생시켜야한다.")
    void testCreationLessThanMinimumPercent() {
        // given
        int lessThanMinimumPercent = MINIMUM_PERCENT - 1;

        assertThat(lessThanMinimumPercent, greaterThanOrEqualTo(Integer.MIN_VALUE));

        // when
        // then
        assertThrows(IllegalArgumentException.class,
                () -> new PercentDiscountVoucher(lessThanMinimumPercent));
    }

    @Test
    @DisplayName("생성 시 최대 할인 퍼센트 보다 클 경우 오류를 발생시켜야한다.")
    void testCreationGreaterThanMaximumPercent() {
        // given
        int greaterThanMaximumPercent = MAXIMUM_PERCENT + 1;

        assertThat(greaterThanMaximumPercent, lessThanOrEqualTo(Integer.MAX_VALUE));

        // when
        // then
        assertThrows(IllegalArgumentException.class,
                () -> new PercentDiscountVoucher(greaterThanMaximumPercent));
    }
}
