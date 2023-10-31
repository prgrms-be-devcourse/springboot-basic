package team.marco.voucher_management_system.repository.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import team.marco.voucher_management_system.domain.voucher.Voucher;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static team.marco.voucher_management_system.domain.voucher.VoucherType.FIXED;
import static team.marco.voucher_management_system.domain.voucher.VoucherType.PERCENT;

@ActiveProfiles("test")
class JsonFileVoucherRepositoryTest {
    private JsonFileVoucherRepository fileVoucherRepository;

    public JsonFileVoucherRepositoryTest() {
        fileVoucherRepository = new JsonFileVoucherRepository("src/test/resources/test_voucher_data.json");
    }

    @DisplayName("바우처를 생성할 수 있다.")
    @Test
    void save() {
        // 1,000원 할인 쿠폰 생성
        int amount = 1_000;
        Voucher voucher = createFixedVoucher(1L, amount);
        Voucher saved = fileVoucherRepository.save(voucher);

        // 생성된 바우처 반환
        assertThat(saved).isEqualTo(voucher);
    }

    @DisplayName("전체 쿠폰 목록을 조회 할 수 있다.")
    @Test
    void findAll() {
        // 1,000원 할인 쿠폰, 10% 할인 쿠폰 생성
        int discountAmount = 1_000;
        Voucher voucher = createFixedVoucher(1L, discountAmount);
        fileVoucherRepository.save(voucher);

        int percent = 10;
        Voucher voucher2 = createPercentVoucher(2L, percent);
        fileVoucherRepository.save(voucher2);

        // 전체 쿠폰 목록 조회
        List<Voucher> vouchers = fileVoucherRepository.findAll();

        // 저장된 2개의 쿠폰이 조회
        assertThat(vouchers).hasSize(2);
    }

    @DisplayName("쿠폰 id로 쿠폰을 검색할 수 있다.")
    @Test
    void findById() {
        // given
        Voucher voucher = createFixedVoucher(1L, 1000);
        fileVoucherRepository.save(voucher);
        Long voucherId = voucher.getId();

        // when
        Optional<Voucher> found = fileVoucherRepository.findById(voucherId);

        // then
        assertThat(found.get()).isNotNull();
        assertThat(found.get())
                .extracting("id", "discountValue")
                .containsExactly(1L, 1000);
    }

    @DisplayName("존재하지 않는 쿠폰 id로 검색하면 빈 Optional이 반환된다.")
    @Test
    void findByIdWithInvaildId() {
        // given
        Long randomId = -1L;

        // when
        Optional<Voucher> found = fileVoucherRepository.findById(randomId);

        // then
        assertThat(found.isEmpty()).isTrue();
    }

    @DisplayName("쿠폰 id로 쿠폰을 삭제할 수 있다.")
    @Test
    void deleteById() {
        // given
        Voucher voucher = createFixedVoucher(1L, 1000);
        fileVoucherRepository.save(voucher);
        Long voucherId = voucher.getId();

        Optional<Voucher> before = fileVoucherRepository.findById(voucherId);
        assertThatNoException().isThrownBy(before::get);

        // when
        fileVoucherRepository.deleteById(voucherId);

        // then
        Optional<Voucher> after = fileVoucherRepository.findById(voucherId);
        assertThatThrownBy(after::get)
                .isInstanceOf(NoSuchElementException.class);
    }

    @DisplayName("저장된 쿠폰 id 중 최댓값을 구할 수 있다.")
    @Test
    void findLatestVoucherId() {
        // given
        Voucher voucher1 = createFixedVoucher(1L, 1000);
        Voucher voucher2 = createFixedVoucher(2L, 2000);
        Voucher voucher3 = createPercentVoucher(3L, 30);
        fileVoucherRepository.save(voucher1);
        fileVoucherRepository.save(voucher2);
        fileVoucherRepository.save(voucher3);

        // when
        Optional<Long> latestId = fileVoucherRepository.findLatestVoucherId();

        // then
        assertThat(latestId.isEmpty()).isFalse();
        assertThat(latestId.get()).isEqualTo(3L);
    }

    @DisplayName("저장된 쿠폰이 없는 경우 쿠폰 id 중 최댓값을 요청하면 빈 Optional을 반환한다.")
    @Test
    void findLatestVoucherIdInEmpty() {
        // given when
        Optional<Long> latestId = fileVoucherRepository.findLatestVoucherId();

        // then
        assertThat(latestId.isEmpty()).isTrue();
    }

    private static Voucher createFixedVoucher(Long id, int amount) {
        return new Voucher.Builder(id, FIXED, amount).build();
    }

    private static Voucher createPercentVoucher(Long id, int percent) {
        return new Voucher.Builder(id, PERCENT, percent).build();
    }

}