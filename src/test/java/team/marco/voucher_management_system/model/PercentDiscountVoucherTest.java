package team.marco.voucher_management_system.model;

import static java.text.MessageFormat.format;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import team.marco.voucher_management_system.type_enum.VoucherType;

class PercentDiscountVoucherTest extends VoucherTest {
    private static final VoucherType PERCENT_VOUCHER_TYPE = VoucherType.PERCENT;
    private static final int MAXIMUM_PERCENT = 100;
    private static final int MINIMUM_PERCENT = 1;

    @Override
    protected int generateValidData() {
        return (MAXIMUM_PERCENT + MINIMUM_PERCENT) / 2;
    }

    @Override
    protected Voucher generateVoucher(int data) {
        return new PercentDiscountVoucher(data);
    }

    @Test
    @DisplayName("올바른 파라미터를 넘겼을 때 바우처가 생성되야 한다.")
    void testCreation() {
        // given
        int percent = generateValidData();

        // when
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(percent);

        //then
        VoucherType voucherType = percentDiscountVoucher.getType();
        int data = percentDiscountVoucher.getData();

        assertThat(voucherType, is(PERCENT_VOUCHER_TYPE));
        assertThat(data, is(percent));
    }

    @Test
    @DisplayName("생성 시 최소 할인 퍼센트 보다 작을 경우 오류를 발생 시켜야 한다.")
    void testCreationLessThanMinimumPercent() {
        // given
        int lessThanMinimumPercent = MINIMUM_PERCENT - 1;

        assertThat(lessThanMinimumPercent, greaterThanOrEqualTo(Integer.MIN_VALUE));

        // when
        ThrowingCallable targetMethod = () -> new PercentDiscountVoucher(lessThanMinimumPercent);

        //
        String expectedMessage = format("{0}: 할인율은 {1}% 보다 작을 수 없습니다.",
                lessThanMinimumPercent, MINIMUM_PERCENT);

        // when
        assertThatIllegalArgumentException().isThrownBy(targetMethod)
                .withMessage(expectedMessage);
    }

    @Test
    @DisplayName("생성 시 최대 할인 퍼센트 보다 클 경우 오류를 발생 시켜야 한다.")
    void testCreationGreaterThanMaximumPercent() {
        // given
        int greaterThanMaximumPercent = MAXIMUM_PERCENT + 1;

        assertThat(greaterThanMaximumPercent, lessThanOrEqualTo(Integer.MAX_VALUE));

        // when
        ThrowingCallable targetMethod = () -> new PercentDiscountVoucher(greaterThanMaximumPercent);

        //
        String expectedMessage = format("{0}: 할인율은 {1}%를 초과할 수 없습니다.",
                greaterThanMaximumPercent, MAXIMUM_PERCENT);

        // when
        assertThatIllegalArgumentException().isThrownBy(targetMethod)
                .withMessage(expectedMessage);
    }
}
