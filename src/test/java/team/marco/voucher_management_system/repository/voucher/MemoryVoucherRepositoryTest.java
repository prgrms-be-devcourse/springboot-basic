package team.marco.voucher_management_system.repository.voucher;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import team.marco.voucher_management_system.domain.voucher.FixedAmountVoucher;
import team.marco.voucher_management_system.domain.voucher.PercentDiscountVoucher;
import team.marco.voucher_management_system.domain.voucher.Voucher;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryVoucherRepositoryTest {
    private MemoryVoucherRepository voucherRepository;

    @BeforeEach
    void setUp() {
        voucherRepository = new MemoryVoucherRepository();
    }

    @Test
    @DisplayName("바우처 생성 시 생성된 바우처를  반환")
    void 쿠폰_생성_성공() {
        // 1,000원 할인 쿠폰 생성
        int discountAmount = 1_000;
        Voucher voucher = new FixedAmountVoucher(discountAmount);
        Voucher saved = voucherRepository.save(voucher);

        // 생성된 바우처 반환
        assertThat(saved).isEqualTo(voucher);
    }

    @Test
    void 전체_쿠폰_목록_조회_성공() {
        // 1,000원 할인 쿠폰, 10% 할인 쿠폰 생성
        int discountAmount = 1_000;
        Voucher voucher = new FixedAmountVoucher(discountAmount);
        voucherRepository.save(voucher);

        int discountPercent = 10;
        Voucher voucher2 = new PercentDiscountVoucher(discountPercent);
        voucherRepository.save(voucher2);

        // 전체 쿠폰 목록 조회
        List<Voucher> vouchers = voucherRepository.findAll();

        // 저장된 2개의 쿠폰이 조회
        assertThat(vouchers).hasSize(2);
    }
}