package org.prgrms.kdt.voucher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Created by yhh1056
 * Date: 2021/08/25 Time: 1:30 오후
 */
class VoucherDataTest {

    private static final String FIX_VOUCHER = "1";
    private static final String PERCENT_VOUCHER = "2";

    @Test
    @DisplayName("VoucherData 정상 생성 테스트")
    void createVoucherData() {
        VoucherData voucherData = new VoucherData(FIX_VOUCHER,  100L);

        // assertj
        assertThat(voucherData.getVoucherNumber()).isEqualTo("1");
        assertThat(voucherData.getDiscount()).isEqualTo(100L);

        // hamcrest
        MatcherAssert.assertThat(voucherData.getVoucherNumber(), is("1"));
        MatcherAssert.assertThat(voucherData.getDiscount(), is(100L));
    }

    @Test
    @DisplayName("바우처를 선택하지 않은 경우 예외 테스트")
    void createVoucherData_fail_to_empty_voucher() {
        assertThatThrownBy(() -> new VoucherData(" ", 100L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("바우처를 선택해주세요.");
    }

    @Test
    @DisplayName("해당 바우처 번호가 아닌경우 예외 테스트")
    void createVoucherData_fail_not_number() {
        assertThatThrownBy(() -> new VoucherData("3", 100L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당하는 번호를 입력해주세요.");
    }

    @Test
    @DisplayName("할인 수치가 0보다 낮을 경우 예외 테스트")
    void createVoucherData_fail_to_zero() {
        assertThatThrownBy(() -> new VoucherData( FIX_VOUCHER,-1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인금액 또는 할인율은 0보다 작을 수 없습니다.");
    }

    @Test
    @DisplayName("할인 퍼센트가 100이 넘는 예외 테스트")
    void createVoucherData_fail_to_100() {
        assertThatThrownBy(() -> new VoucherData( PERCENT_VOUCHER, 101L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인율은 100 이하로 입력해주세요.");
    }
}