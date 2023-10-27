package team.marco.vouchermanagementsystem.model.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FixedAmountVoucherTest {

    @Test
    void 고정금액_할인쿠폰_생성_성공() {
        // 10,000원 할인 쿠폰 생성
        int discountAmount = 10_000;
        FixedAmountVoucher voucher = new FixedAmountVoucher(discountAmount);

        // 1. UUID가 자동으로 할당
        assertThat(voucher.getId()).isNotNull();
        // 2. VoucherType은 자동으로 FIXED
        assertThat(voucher.getType()).isEqualTo(VoucherType.FIXED);
        // 3. 입력한 비율만큼 할인
        assertThat(voucher.getAmount()).isEqualTo(discountAmount);
    }

    @Test
    @DisplayName("UUID 값와 함께 할인 쿠폰 생성 가능")
    void 고정비율_할인쿠폰_생성_성공_2() {
        // 특정 UUID 값을 가진 10,000원 할인 쿠폰 생성
        UUID voucherId = UUID.randomUUID();
        int discountAmount = 10_000;
        FixedAmountVoucher voucher = new FixedAmountVoucher(voucherId, discountAmount);

        // 1. 입력한 UUID로 생성
        assertThat(voucher.getId()).isEqualTo(voucherId);
        // 2. VoucherType은 자동으로 FIXED
        assertThat(voucher.getType()).isEqualTo(VoucherType.FIXED);
        // 3. 입력한 비율만큼 할인
        assertThat(voucher.getAmount()).isEqualTo(discountAmount);
    }

    @Test
    @DisplayName("할인 금액은 100원보다 작을 수 없다.")
    void 고정금액_할인쿠폰_생성_실패() {
        // 1. 유효하지 않은 할인 금액
        int invalidAmount = 10;
        // 2. 유효한 할인 금액 (엣지포인트)
        int validAmount = 100;

        // 1. 10원 할인 쿠폰 생성 시 에러 발생
        assertThrows(IllegalArgumentException.class, () -> {
            new FixedAmountVoucher(invalidAmount);
        });

        // 2. 100원 할인 쿠폰 생성 시 에러 발생 X
        assertDoesNotThrow(() -> { new FixedAmountVoucher(validAmount); });
    }

    @Test
    @DisplayName("할인 금액은 100만원보다 클 수 없다.")
    void 고정금액_할인쿠폰_생성_실패_2() {
        // 1. 유효하지 않은 할인 금액
        int invalidAmount = 1_000_010;
        // 2. 유효한 할인 금액 (엣지포인트)
        int validAmount = 1_000_000;

        // 1. 100,010원 할인 쿠폰 생성 시 에러 발생
        assertThrows(IllegalArgumentException.class, () -> {
            new FixedAmountVoucher(invalidAmount);
        });

        // 2. 100,000원 할인 쿠폰 생성 시 에러 발생 X
        assertDoesNotThrow(() -> { new FixedAmountVoucher(validAmount); });
    }

    @Test
    @DisplayName("UUID와 할인 금액으로 쿠폰을 생성하는 경우에도 유효성 검사 실행")
    void 고정비율_할인쿠폰_생성_실패_4() {
        // UUID + 유효하지 않은 할인 비율
        UUID voucherId = UUID.randomUUID();
        int invalidAmount = -100;

        // 마이너스(-) 금액 할인 쿠폰 생성 시 에러 발생
        assertThrows(IllegalArgumentException.class, () -> {
            new PercentDiscountVoucher(voucherId, invalidAmount);
        });
    }

}