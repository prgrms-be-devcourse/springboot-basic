package com.programmers.vouchermanagement.repository.voucher;

import com.programmers.vouchermanagement.domain.voucher.FixedAmountVoucher;
import com.programmers.vouchermanagement.domain.voucher.PercentDiscountVoucher;
import com.programmers.vouchermanagement.domain.voucher.Voucher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class VoucherRepositoryTest {

    @Autowired
    private VoucherRepository voucherRepository;

    @BeforeEach
    void setUp() {
        voucherRepository.deleteAll();
    }

    @Test
    @DisplayName("바우처를 저장할 수 있다.")
    void save() {
        // given
        Voucher newVoucher = FixedAmountVoucher.fixture();

        // when
        voucherRepository.save(newVoucher);

        // then
        Voucher savedVoucher = voucherRepository.findAll().get(0);
        assertThat(savedVoucher.getType()).isEqualTo(newVoucher.getType());
        assertThat(savedVoucher.getAmount()).isEqualTo(newVoucher.getAmount());
    }

    @Test
    @DisplayName("바우처 목록을 저장할 수 있다.")
    void saveAll() {
        // given
        Voucher newVoucher1 = FixedAmountVoucher.fixture();
        Voucher newVoucher2 = PercentDiscountVoucher.fixture();

        // when
        voucherRepository.saveAll(List.of(newVoucher1, newVoucher2));

        // then
        List<Voucher> savedVouchers = voucherRepository.findAll();

        assertThat(savedVouchers).hasSize(2);
        assertThat(savedVouchers).extracting(Voucher::getType)
                .containsExactlyInAnyOrder(newVoucher1.getType(), newVoucher2.getType());
        assertThat(savedVouchers).extracting(Voucher::getAmount)
                .containsExactlyInAnyOrder(newVoucher1.getAmount(), newVoucher2.getAmount());
    }

    @Test
    @DisplayName("바우처를 아이디로 조회할 수 있다.")
    void findById() {
        // given
        Voucher newVoucher = FixedAmountVoucher.fixture();
        voucherRepository.save(newVoucher);

        Voucher savedVoucher = voucherRepository.findAll().get(0);

        // when
        Optional<Voucher> foundVoucher = voucherRepository.findById(savedVoucher.getId());

        // then
        assertThat(foundVoucher).isPresent();
        assertThat(foundVoucher.get().getId()).isEqualTo(savedVoucher.getId());
        assertThat(foundVoucher.get().getType()).isEqualTo(savedVoucher.getType());
    }

    @Test
    @DisplayName("모든 바우처를 조회할 수 있다.")
    void findAll() {
        // given
        Voucher newVoucher1 = FixedAmountVoucher.fixture();
        Voucher newVoucher2 = PercentDiscountVoucher.fixture();
        voucherRepository.saveAll(List.of(newVoucher1, newVoucher2));

        // when
        List<Voucher> foundVouchers = voucherRepository.findAll();

        // then
        assertThat(foundVouchers).hasSize(2);
        assertThat(foundVouchers).extracting(Voucher::getType)
                .containsExactlyInAnyOrder(newVoucher1.getType(), newVoucher2.getType());
        assertThat(foundVouchers).extracting(Voucher::getAmount)
                .containsExactlyInAnyOrder(newVoucher1.getAmount(), newVoucher2.getAmount());
    }

    @Test
    @DisplayName("바우처를 수정할 수 있다.")
    void update() {
        // given
        Voucher newVoucher = FixedAmountVoucher.fixture();
        voucherRepository.save(newVoucher);

        Voucher savedVoucher = voucherRepository.findAll().get(0);

        // when
        long newAmountValue = 2000L;
        voucherRepository.updateById(savedVoucher.getId(), new FixedAmountVoucher(newAmountValue));

        // then
        Optional<Voucher> updatedVoucher = voucherRepository.findById(savedVoucher.getId());
        assertThat(updatedVoucher).isPresent();
        assertThat(updatedVoucher.get().getId()).isEqualTo(savedVoucher.getId());
        assertThat(updatedVoucher.get().getType()).isEqualTo(savedVoucher.getType());
        assertThat(updatedVoucher.get().getAmount()).isEqualTo(newAmountValue);
    }

    @Test
    @DisplayName("바우처를 아이디로 삭제할 수 있다.")
    void deleteById() {
        // given
        Voucher newVoucher = FixedAmountVoucher.fixture();
        voucherRepository.save(newVoucher);

        Voucher savedVoucher = voucherRepository.findAll().get(0);

        // when
        voucherRepository.deleteById(savedVoucher.getId());

        // then
        Optional<Voucher> foundVoucher = voucherRepository.findById(savedVoucher.getId());
        assertThat(foundVoucher).isEmpty();
    }

    @Test
    @DisplayName("모든 바우처를 삭제할 수 있다.")
    void deleteAll() {
        // given
        voucherRepository.saveAll(List.of(FixedAmountVoucher.fixture(), PercentDiscountVoucher.fixture()));

        // when
        voucherRepository.deleteAll();

        // then
        List<Voucher> foundVouchers = voucherRepository.findAll();
        assertThat(foundVouchers).isEmpty();
    }
}
