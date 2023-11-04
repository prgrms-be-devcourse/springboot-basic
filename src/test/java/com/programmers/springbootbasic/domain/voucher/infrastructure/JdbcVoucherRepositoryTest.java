package com.programmers.springbootbasic.domain.voucher.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;

import com.programmers.springbootbasic.common.TimeGenerator;
import com.programmers.springbootbasic.domain.TestTimeGenerator;
import com.programmers.springbootbasic.domain.voucher.domain.ProdVoucherIdGenerator;
import com.programmers.springbootbasic.domain.voucher.domain.VoucherIdGenerator;
import com.programmers.springbootbasic.domain.voucher.domain.VoucherRepository;
import com.programmers.springbootbasic.domain.voucher.domain.VoucherType.FixedAmountVoucher;
import com.programmers.springbootbasic.domain.voucher.domain.VoucherType.PercentDiscountVoucher;
import com.programmers.springbootbasic.domain.voucher.domain.VoucherType.VoucherTypeEnum;
import com.programmers.springbootbasic.domain.voucher.domain.entity.Voucher;
import com.programmers.springbootbasic.domain.voucher.presentation.dto.VoucherCriteria;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@DisplayName("JdbcVoucherRepositoryTest 테스트")
class JdbcVoucherRepositoryTest {

    @Autowired
    private VoucherRepository voucherRepository;
    private final VoucherIdGenerator idGenerator = new ProdVoucherIdGenerator();
    private final TimeGenerator timeGenerator = new TestTimeGenerator();

    @DisplayName("Voucher를 저장할 수 있다.")
    @Test
    void success_save() {
        // given
        Voucher voucher = new Voucher(idGenerator.generate(), new FixedAmountVoucher(100), 100,
            timeGenerator.now());

        // when
        Voucher savedVoucher = voucherRepository.save(voucher);

        // then
        assertThat(voucherRepository.findAll()).hasSize(1);
        assertThat(savedVoucher).isEqualTo(voucher);
    }

    @DisplayName("존재하는 Voucher Id 값으로 Voucher를 조회할 수 있다.")
    @Test
    void success_findById() {
        // given
        Voucher voucher = new Voucher(idGenerator.generate(), new FixedAmountVoucher(100), 100,
            timeGenerator.now());
        voucherRepository.save(voucher);

        // when
        var result = voucherRepository.findById(voucher.getId());

        // then
        assertThat(result).isPresent().get().isEqualTo(voucher);
    }

    @DisplayName("모든 Voucher를 조회할 수 있다.")
    @Test
    void success_findAll() {
        // given
        Voucher voucher1 = new Voucher(idGenerator.generate(), new FixedAmountVoucher(100), 100,
            timeGenerator.now());
        Voucher voucher2 = new Voucher(idGenerator.generate(), new FixedAmountVoucher(200), 200,
            timeGenerator.now());
        voucherRepository.save(voucher1);
        voucherRepository.save(voucher2);

        // when
        var result = voucherRepository.findAll();

        // then
        assertThat(result).hasSize(2);
    }

    @DisplayName("존재하는 Voucher Id 값으로 Voucher를 삭제할 수 있다.")
    @Test
    void success_deleteById() {
        // given
        Voucher voucher = new Voucher(idGenerator.generate(), new FixedAmountVoucher(100), 100,
            timeGenerator.now());
        voucherRepository.save(voucher);

        // when
        int result = voucherRepository.deleteById(voucher.getId());

        // then
        assertThat(result).isEqualTo(1);
        assertThat(voucherRepository.findAll()).isEmpty();
    }

    @DisplayName("존재하지 않는 ID로 Voucher를 삭제할 때, 0을 반환한다.")
    @Test
    void fail_deleteById_notFound() {
        // given
        UUID nonExistentId = idGenerator.generate();

        // when
        int result = voucherRepository.deleteById(nonExistentId);

        // then
        assertThat(result).isZero();
    }

    @DisplayName("Voucher 정보를 업데이트 할 수 있다.")
    @Test
    void success_update() {
        // given
        Voucher voucher = new Voucher(idGenerator.generate(), new FixedAmountVoucher(100), 100,
            timeGenerator.now());
        voucherRepository.save(voucher);
        Voucher updatedVoucher = new Voucher(voucher.getId(), new FixedAmountVoucher(200), 200,
            timeGenerator.now());

        // when
        int result = voucherRepository.update(updatedVoucher);

        // then
        assertThat(result).isEqualTo(1);
        assertThat(voucherRepository.findById(voucher.getId()).get().getBenefitValue())
            .isEqualTo(200);
    }

    @DisplayName("누락된 필드 정보로 Voucher를 업데이트 할 때, 예외가 발생한다.")
    @Test
    void fail_update_missingFields() {
        // given
        Voucher voucher = new Voucher(idGenerator.generate(), new FixedAmountVoucher(100), 100,
            timeGenerator.now());
        voucherRepository.save(voucher);
        Voucher incompleteVoucher = new Voucher(voucher.getId(), null, 200,
            timeGenerator.now()); // Type 정보 누락
        Voucher incompleteVoucher2 = new Voucher(voucher.getId(), new FixedAmountVoucher(200),
            null, timeGenerator.now()); // BenefitValue 정보 누락

        // when && then
        assertThatThrownBy(() -> voucherRepository.update(incompleteVoucher))
            .isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> voucherRepository.update(incompleteVoucher2))
            .isInstanceOf(DataAccessException.class);
    }


    @Test
    void findByCriteria() {
        // given
        Voucher voucher1 = new Voucher(idGenerator.generate(), new FixedAmountVoucher(100), 100,
            LocalDateTime.of(2023, 10, 1, 0, 0, 0));
        Voucher voucher2 = new Voucher(idGenerator.generate(), new PercentDiscountVoucher(50), 50,
            LocalDateTime.of(2023, 10, 3, 0, 0, 0));
        voucherRepository.save(voucher1);
        voucherRepository.save(voucher2);

        // when
        var dateResult = voucherRepository.findByCriteria(new VoucherCriteria(
            LocalDate.of(2023, 10, 1),
            LocalDate.of(2023, 10, 1),
            null
        ));

        var typeResult = voucherRepository.findByCriteria(new VoucherCriteria(
            null,
            null,
            VoucherTypeEnum.FIXED
        ));

        var dateAndTypeResult = voucherRepository.findByCriteria(new VoucherCriteria(
            LocalDate.of(2023, 10, 1),
            LocalDate.of(2023, 10, 1),
            VoucherTypeEnum.FIXED
        ));

        // then
        assertThat(dateResult).hasSize(1).extracting("createdAt")
            .containsExactly(LocalDateTime.of(2023, 10, 1, 0, 0, 0));
        assertThat(typeResult).hasSize(1).extracting("voucherType")
            .containsExactly(new FixedAmountVoucher(100));
        assertThat(dateAndTypeResult).hasSize(1).extracting("createdAt", "voucherType")
            .containsExactly(
                tuple(LocalDateTime.of(2023, 10, 1, 0, 0, 0),
                    new FixedAmountVoucher(100)));
    }
}
