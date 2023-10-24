package com.programmers.vouchermanagement.repository.voucher;

import com.programmers.vouchermanagement.domain.voucher.FixedAmountVoucher;
import com.programmers.vouchermanagement.domain.voucher.PercentDiscountVoucher;
import com.programmers.vouchermanagement.domain.voucher.Voucher;
import com.programmers.vouchermanagement.dto.voucher.UpdateVoucherRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JdbcTemplateVoucherRepositoryTest {

    @Autowired
    private JdbcTemplateVoucherRepository repository;

    @Test
    @DisplayName("바우처를 저장할 수 있다.")
    void save() {
        // given
        Voucher fixedAmountVoucher = FixedAmountVoucher.fixture();

        // when
        repository.save(fixedAmountVoucher);

        // then
        Optional<Voucher> voucher = repository.findById(fixedAmountVoucher.getId());
        assertThat(voucher).isPresent();
        assertThat(voucher.get().getId()).isEqualTo(fixedAmountVoucher.getId());
        assertThat(voucher.get().getType()).isEqualTo(fixedAmountVoucher.getType());
        assertThat(voucher.get().getAmount()).isEqualTo(fixedAmountVoucher.getAmount());
    }

    @Test
    @DisplayName("바우처 목록을 저장할 수 있다.")
    void saveAll() {
        // given
        Voucher fixedAmountVoucher = FixedAmountVoucher.fixture();
        Voucher percentDiscountVoucher = PercentDiscountVoucher.fixture();

        // when
        repository.saveAll(List.of(fixedAmountVoucher, percentDiscountVoucher));

        // then
        List<Voucher> vouchers = repository.findAll();

        assertThat(vouchers).hasSize(2);
        assertThat(vouchers).extracting(Voucher::getId)
                .containsExactlyInAnyOrder(fixedAmountVoucher.getId(), percentDiscountVoucher.getId());
        assertThat(vouchers).extracting(Voucher::getType)
                .containsExactlyInAnyOrder(fixedAmountVoucher.getType(), percentDiscountVoucher.getType());
        assertThat(vouchers).extracting(Voucher::getAmount)
                .containsExactlyInAnyOrder(fixedAmountVoucher.getAmount(), percentDiscountVoucher.getAmount());
    }

    @Test
    @DisplayName("바우처를 아이디로 조회할 수 있다.")
    void findById() {
        // given
        Voucher fixedAmountVoucher = FixedAmountVoucher.fixture();
        repository.save(fixedAmountVoucher);

        // when
        Optional<Voucher> voucher = repository.findById(fixedAmountVoucher.getId());

        // then
        assertThat(voucher).isPresent();
        assertThat(voucher.get().getId()).isEqualTo(fixedAmountVoucher.getId());
        assertThat(voucher.get().getType()).isEqualTo(fixedAmountVoucher.getType());
    }

    @Test
    @DisplayName("모든 바우처를 조회할 수 있다.")
    void findAll() {
        // given
        Voucher fixedAmountVoucher = FixedAmountVoucher.fixture();
        Voucher percentDiscountVoucher = PercentDiscountVoucher.fixture();
        repository.save(fixedAmountVoucher);
        repository.save(percentDiscountVoucher);

        // when
        List<Voucher> vouchers = repository.findAll();

        // then
        assertThat(vouchers).hasSize(2);
        assertThat(vouchers).extracting(Voucher::getId)
                .containsExactlyInAnyOrder(fixedAmountVoucher.getId(), percentDiscountVoucher.getId());
        assertThat(vouchers).extracting(Voucher::getType)
                .containsExactlyInAnyOrder(fixedAmountVoucher.getType(), percentDiscountVoucher.getType());
        assertThat(vouchers).extracting(Voucher::getAmount)
                .containsExactlyInAnyOrder(fixedAmountVoucher.getAmount(), percentDiscountVoucher.getAmount());
    }

    @Test
    @DisplayName("바우처를 아이디로 업데이트할 수 있다.")
    void update() {
        // given
        Voucher fixedAmountVoucher = FixedAmountVoucher.fixture();
        repository.save(fixedAmountVoucher);

        // when
        long newAmountValue = 2000L;
        repository.updateById(fixedAmountVoucher.getId(), new FixedAmountVoucher(newAmountValue));

        // then
        Optional<Voucher> voucher = repository.findById(fixedAmountVoucher.getId());
        assertThat(voucher).isPresent();
        assertThat(voucher.get().getId()).isEqualTo(fixedAmountVoucher.getId());
        assertThat(voucher.get().getType()).isEqualTo(fixedAmountVoucher.getType());
        assertThat(voucher.get().getAmount()).isEqualTo(newAmountValue);
    }

    @Test
    @DisplayName("바우처를 아이디로 삭제할 수 있다.")
    void deleteById() {
        // given
        Voucher fixedAmountVoucher = FixedAmountVoucher.fixture();
        repository.save(fixedAmountVoucher);

        // when
        repository.deleteById(fixedAmountVoucher.getId());

        // then
        Optional<Voucher> voucher = repository.findById(fixedAmountVoucher.getId());
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
