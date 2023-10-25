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
class JdbcTemplateVoucherRepositoryTest {

    @Autowired
    private JdbcTemplateVoucherRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("바우처를 저장할 수 있다.")
    void save() {
        // given
        Voucher newVoucher = FixedAmountVoucher.fixture();

        // when
        repository.save(newVoucher);

        // then
        Voucher foundVoucher = repository.findAll().get(0);
        assertThat(foundVoucher.getType()).isEqualTo(newVoucher.getType());
        assertThat(foundVoucher.getAmount()).isEqualTo(newVoucher.getAmount());
    }

    @Test
    @DisplayName("바우처 목록을 저장할 수 있다.")
    void saveAll() {
        // given
        Voucher newVoucher1 = FixedAmountVoucher.fixture();
        Voucher newVoucher2 = PercentDiscountVoucher.fixture();

        // when
        repository.saveAll(List.of(newVoucher1, newVoucher2));

        // then
        List<Voucher> foundVouchers = repository.findAll();

        assertThat(foundVouchers).hasSize(2);
        assertThat(foundVouchers).extracting(Voucher::getType)
                .containsExactlyInAnyOrder(newVoucher1.getType(), newVoucher2.getType());
        assertThat(foundVouchers).extracting(Voucher::getAmount)
                .containsExactlyInAnyOrder(newVoucher1.getAmount(), newVoucher2.getAmount());
    }

    @Test
    @DisplayName("바우처를 아이디로 조회할 수 있다.")
    void findById() {
        // given
        Voucher newVoucher = FixedAmountVoucher.fixture();
        repository.save(newVoucher);

        Voucher createdVoucher = repository.findAll().get(0);

        // when
        Optional<Voucher> foundVoucher = repository.findById(createdVoucher.getId());

        // then
        assertThat(foundVoucher).isPresent();
        assertThat(foundVoucher.get().getId()).isEqualTo(createdVoucher.getId());
        assertThat(foundVoucher.get().getType()).isEqualTo(createdVoucher.getType());
    }

    @Test
    @DisplayName("모든 바우처를 조회할 수 있다.")
    void findAll() {
        // given
        Voucher newVoucher1 = FixedAmountVoucher.fixture();
        Voucher newVoucher2 = PercentDiscountVoucher.fixture();
        repository.save(newVoucher1);
        repository.save(newVoucher2);

        // when
        List<Voucher> foundVouchers = repository.findAll();

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
        repository.save(newVoucher);

        Voucher createdVoucher = repository.findAll().get(0);

        // when
        long newAmountValue = 2000L;
        repository.updateById(createdVoucher.getId(), new FixedAmountVoucher(newAmountValue));

        // then
        Optional<Voucher> foundVoucher = repository.findById(createdVoucher.getId());
        assertThat(foundVoucher).isPresent();
        assertThat(foundVoucher.get().getId()).isEqualTo(createdVoucher.getId());
        assertThat(foundVoucher.get().getType()).isEqualTo(createdVoucher.getType());
        assertThat(foundVoucher.get().getAmount()).isEqualTo(newAmountValue);
    }

    @Test
    @DisplayName("바우처를 아이디로 삭제할 수 있다.")
    void deleteById() {
        // given
        Voucher fixedAmountVoucher = FixedAmountVoucher.fixture();
        repository.save(fixedAmountVoucher);

        Voucher foundFixedAmountVoucher = repository.findAll().get(0);

        // when
        repository.deleteById(foundFixedAmountVoucher.getId());

        // then
        Optional<Voucher> voucher = repository.findById(foundFixedAmountVoucher.getId());
        assertThat(voucher).isEmpty();
    }

    @Test
    @DisplayName("모든 바우처를 삭제할 수 있다.")
    void deleteAll() {
        // given
        repository.saveAll(List.of(FixedAmountVoucher.fixture(), PercentDiscountVoucher.fixture()));

        // when
        repository.deleteAll();

        // then
        List<Voucher> vouchers = repository.findAll();
        assertThat(vouchers).isEmpty();
    }
}
