package team.marco.voucher_management_system.repository.voucher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import team.marco.voucher_management_system.domain.voucher.Voucher;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static team.marco.voucher_management_system.domain.voucher.VoucherType.FIXED;
import static team.marco.voucher_management_system.domain.voucher.VoucherType.PERCENT;

@Transactional
@SpringBootTest
@ActiveProfiles("prod")
class JdbcVoucherRepositoryTest {
    @Autowired
    private JdbcVoucherRepository voucherRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        String query = "DELETE FROM vouchers";
        jdbcTemplate.update(query);
    }

    @Test
    @DisplayName("바우처 생성 시 생성된 바우처를  반환")
    void 쿠폰_생성_성공() {
        // 1,000원 할인 쿠폰 생성
        int amount = 1_000;
        Voucher voucher = createFixedVoucher(1L, amount);
        Voucher saved = voucherRepository.save(voucher);

        // 생성된 바우처 반환
        assertThat(saved).isEqualTo(voucher);
    }

    @Test
    void 전체_쿠폰_목록_조회_성공() {
        // 1,000원 할인 쿠폰, 10% 할인 쿠폰 생성
        int amount = 1_000;
        Voucher voucher = createFixedVoucher(1L, amount);
        voucherRepository.save(voucher);

        int percent = 10;
        Voucher voucher2 = createPercentVoucher(2L, percent);
        voucherRepository.save(voucher2);

        // 전체 쿠폰 목록 조회
        List<Voucher> vouchers = voucherRepository.findAll();

        // 저장된 2개의 쿠폰이 조회
        assertThat(vouchers).hasSize(2);
    }

    @Test
    void 쿠폰_아이디로_조회_성공() {
        // 1,000원 할인 쿠폰 생성
        int amount = 1_000;
        Voucher voucher = createFixedVoucher(1L, amount);
        voucherRepository.save(voucher);

        // 쿠폰 번호로 조회
        Voucher found = voucherRepository.findById(voucher.getId()).get();

        // 저장한 쿠폰과 동일한 쿠폰 반환
        assertThat(found.getId()).isEqualTo(voucher.getId());
    }

    @Test
    void 쿠폰_번호로_삭제_성공() {
        // 1,000원 할인 쿠폰 생성
        int amount = 1_000;
        Voucher voucher = createFixedVoucher(1L, amount);
        voucherRepository.save(voucher);

        // 쿠폰 삭제
        voucherRepository.deleteById(voucher.getId());

        // 쿠폰이 삭제됨
        List<Voucher> vouchers = voucherRepository.findAll();
        assertThat(vouchers).hasSize(0);
    }

    private static Voucher createFixedVoucher(Long id, int amount) {
        return new Voucher.Builder(id, FIXED, amount).build();
    }

    private static Voucher createPercentVoucher(Long id, int percent) {
        return new Voucher.Builder(id, PERCENT, percent).build();
    }
}