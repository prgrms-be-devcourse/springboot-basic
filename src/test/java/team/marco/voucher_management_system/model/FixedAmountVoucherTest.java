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

class FixedAmountVoucherTest extends VoucherTest {
    private static final VoucherType FIXED_VOUCHER_TYPE = VoucherType.FIXED;
    private static final int MAXIMUM_AMOUNT = (int) 1e9;
    private static final int MINIMUM_AMOUNT = 1;

    @Override
    protected int generateValidData() {
        return (MAXIMUM_AMOUNT + MINIMUM_AMOUNT) / 2;
    }

    @Override
    protected Voucher generateVoucher(int data) {
        return new FixedAmountVoucher(data);
    }

    @Test
    @DisplayName("올바른 파라미터를 넘겼을 때 바우처가 생성되야 한다.")
    void testCreation() {
        // given
        int amount = generateValidData();

        // when
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(amount);

        //then
        VoucherType voucherType = fixedAmountVoucher.getType();
        int data = fixedAmountVoucher.getData();

        assertThat(voucherType, is(FIXED_VOUCHER_TYPE));
        assertThat(data, is(amount));
    }

    @Test
    @DisplayName("생성 시 최소 할인 금액 보다 작을 경우 오류를 발생 시켜야 한다.")
    void testCreationLessThanMinimumAmount() {
        // given
        int lessThanMinimumAmount = MINIMUM_AMOUNT - 1;

        assertThat(lessThanMinimumAmount, greaterThanOrEqualTo(Integer.MIN_VALUE));

        // when
        ThrowingCallable targetMethod = () -> new FixedAmountVoucher(lessThanMinimumAmount);

        // then
        String expectedMessage = format("{0}: 할인 금액은 {1} 보다 작을 수 없습니다.",
                lessThanMinimumAmount, MINIMUM_AMOUNT);

        assertThatIllegalArgumentException().isThrownBy(targetMethod)
                .withMessage(expectedMessage);
    }

    @Test
    @DisplayName("생성 시 최대 할인 금액 보다 클 경우 오류를 발생 시켜야 한다.")
    void testCreationGreaterThanMaximumAmount() {
        // given
        int greaterThanMaximumAmount = MAXIMUM_AMOUNT + 1;

        assertThat(greaterThanMaximumAmount, lessThanOrEqualTo(Integer.MAX_VALUE));

        // when
        ThrowingCallable targetMethod = () -> new FixedAmountVoucher(greaterThanMaximumAmount);

        // then
        String expectedMessage = format("{0}: 할인 금액은 {1} 보다 클 수 없습니다.",
                greaterThanMaximumAmount, MAXIMUM_AMOUNT);

        assertThatIllegalArgumentException().isThrownBy(targetMethod)
                .withMessage(expectedMessage);
    }
}
