package team.marco.voucher_management_system.model.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PercentDiscountVoucherTest {
    @Test
    void 고정비율_할인쿠폰_생성_성공() {
        // 20% 할인 쿠폰 생성
        int discountPercent = 20;
        PercentDiscountVoucher voucher = new PercentDiscountVoucher(discountPercent);

        // 1. UUID가 자동으로 할당
        assertThat(voucher.getId()).isNotNull();
        // 2. VoucherType은 자동으로 PERCENT
        assertThat(voucher.getType()).isEqualTo(VoucherType.PERCENT);
        // 3. 입력한 비율만큼 할인
        assertThat(voucher.getPercent()).isEqualTo(discountPercent);
    }

    @Test
    @DisplayName("UUID 값와 함께 할인 쿠폰 생성 가능")
    void 고정비율_할인쿠폰_생성_성공_2() {
        // 특정 UUID 값을 가진 20% 할인 쿠폰 생성
        UUID voucherId = UUID.randomUUID();
        int discountPercent = 20;
        PercentDiscountVoucher voucher = new PercentDiscountVoucher(voucherId, discountPercent);

        // 1. 입력한 UUID로 생성
        assertThat(voucher.getId()).isEqualTo(voucherId);
        // 2. VoucherType은 자동으로 PERCENT
        assertThat(voucher.getType()).isEqualTo(VoucherType.PERCENT);
        // 3. 입력한 비율만큼 할인
        assertThat(voucher.getPercent()).isEqualTo(discountPercent);
    }

    @Test
    @DisplayName("할인 비율은 0%일 수 없다.")
    void 고정비율_할인쿠폰_생성_실패() {
        // 유효하지 않은 할인 비율
        int zeroPercent = 0;

        // 0% 할인 쿠폰 생성 시 에러 발생
        assertThrows(IllegalArgumentException.class, () -> {
            new PercentDiscountVoucher(zeroPercent);
        });
    }

    @Test
    @DisplayName("할인 비율은 마이너스(-)일 수 없다.")
    void 고정비율_할인쿠폰_생성_실패_2() {
        // 유효하지 않은 할인 비율
        int minusPercent = -10;

        // -10% 할인 쿠폰 생성 시 에러 발생
        assertThrows(IllegalArgumentException.class, () -> {
            new PercentDiscountVoucher(minusPercent);
        });
    }

    @Test
    @DisplayName("할인 비율은 50%를 넘을 수 없다.")
    void 고정비율_할인쿠폰_생성_실패_3() {
        // 유효하지 않은 할인 비율
        int highPercent = 90;

        // 할인 비율이 50%가 넘는 할인 쿠폰 생성 시 에러 발생
        assertThrows(IllegalArgumentException.class, () -> {
            new PercentDiscountVoucher(highPercent);
        });
    }

    @Test
    @DisplayName("UUID와 할인 비율로 쿠폰을 생성하는 경우에도 유효성 검사 실행")
    void 고정비율_할인쿠폰_생성_실패_4() {
        // UUID + 유효하지 않은 할인 비율
        UUID voucherId = UUID.randomUUID();
        int highPercent = 90;

        // 할인 비율이 50%가 넘는 할인 쿠폰 생성 시 에러 발생 2
        assertThrows(IllegalArgumentException.class, () -> {
            new PercentDiscountVoucher(voucherId, highPercent);
        });
    }
}