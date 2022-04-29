package org.programmers.springbootbasic.voucher;

import org.junit.jupiter.api.*;
import org.programmers.springbootbasic.config.DBConfig;
import org.programmers.springbootbasic.exception.NotInsertException;
import org.programmers.springbootbasic.voucher.model.FixedAmountVoucher;
import org.programmers.springbootbasic.voucher.model.Voucher;
import org.programmers.springbootbasic.voucher.model.VoucherType;
import org.programmers.springbootbasic.voucher.repository.JdbcVoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.programmers.springbootbasic.config.DBConfig.dbSetup;

@SpringJUnitConfig
@ContextConfiguration(classes = {DBConfig.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcVoucherRepositoryTest {

    @Autowired
    JdbcVoucherRepository jdbcVoucherRepository;

    @BeforeAll
    void setup() {
        dbSetup();
    }

    @AfterEach
    void cleanup() {
        jdbcVoucherRepository.deleteAll();
    }

    @Test
    @DisplayName("고객을 추가 할 수 있다.")
    void testInsert() {
        VoucherType fixedAmountType = VoucherType.FIXED;
        Voucher fixedAmountVoucher = fixedAmountType.create(UUID.randomUUID(), 100L ,LocalDateTime.now());
        jdbcVoucherRepository.insert(fixedAmountVoucher);

        var retrievedVoucher = jdbcVoucherRepository.findById(fixedAmountVoucher.getVoucherId());

        assertThat(retrievedVoucher).isPresent().get().isEqualTo(fixedAmountVoucher);
    }

    @Test
    @DisplayName("전체 고객을 조회 할 수 있다.")
    void testFindAll() {
        VoucherType fixedAmountType = VoucherType.FIXED;
        Voucher fixedAmountVoucher = fixedAmountType.create(UUID.randomUUID(), 100L ,LocalDateTime.now());
        jdbcVoucherRepository.insert(fixedAmountVoucher);
        var vouchers = jdbcVoucherRepository.findAll();

        assertThat(vouchers).hasSize(1);
    }

    @Test
    @DisplayName("아이디로 고객을 조회 할 수 있다.")
    void testFindByName() {
        VoucherType fixedAmountType = VoucherType.FIXED;
        Voucher fixedAmountVoucher = fixedAmountType.create(UUID.randomUUID(), 100L ,LocalDateTime.now());
        jdbcVoucherRepository.insert(fixedAmountVoucher);
        var vouchers = jdbcVoucherRepository.findById(fixedAmountVoucher.getVoucherId());
        assertThat(vouchers).isPresent();

        var unknown = jdbcVoucherRepository.findById(UUID.randomUUID());
        assertThat(unknown).isNotPresent();
    }

    @Test
    @DisplayName("생성일 순으로 고객을 조회 할 수 있다.")
    void testFindByCreatedAt() {
        var vouchers = jdbcVoucherRepository.findByCreatedAt();

        assertThat(vouchers).hasSize(0);
    }

    @Test
    @DisplayName("바우처를 수정 할 수 있다.")
    void testUpdate() {
        VoucherType fixedAmountType = VoucherType.FIXED;
        Voucher fixedAmountVoucher = fixedAmountType.create(UUID.randomUUID(), 100L ,LocalDateTime.now());
        jdbcVoucherRepository.insert(fixedAmountVoucher);
        fixedAmountVoucher.changeValue(300L);
        jdbcVoucherRepository.update(fixedAmountVoucher);

        var all = jdbcVoucherRepository.findAll();
        assertThat(all).hasSize(1).containsOnlyOnce(fixedAmountVoucher);

        var retrievedVoucher = jdbcVoucherRepository.findById(fixedAmountVoucher.getVoucherId());
        assertThat(retrievedVoucher).isPresent().get().isEqualTo(fixedAmountVoucher);
    }

    @Test
    @DisplayName("중복 바우처 테스트")
    void duplicateVoucher() {
        var fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 1000L, LocalDateTime.now());
        jdbcVoucherRepository.insert(fixedAmountVoucher);

        assertThatThrownBy(() -> jdbcVoucherRepository.insert(fixedAmountVoucher))
                .isInstanceOf(NotInsertException.class);
    }
}